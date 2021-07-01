package com.sentia.shopdemo.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


@Component
@ConditionalOnProperty(prefix = "changelog", name = "switch")
public class DatabaseChangelog {

    private static final Logger log = LoggerFactory.getLogger(DatabaseChangelog.class);
    private final String ENTITY_NAME = "DatabaseChangelog";
    private final String COLORS = "colors";
    private final String WRONG_LABLE = "lable";

    private final String LABEL = "label";
    private final String TYPE = "type";
    private final String COLOR = "color";
    private final String HEX = "hex";
    private final String PRICE = "price";
    private final String CURRENCY = "currency";
    private final String SRC = "src";
    private final String CODE = "code";


    @Autowired
    ProductRepository productRepository;

    @Value("${data.set}")
    private String filePath;

    @EventListener(ApplicationReadyEvent.class)
    private void populateDatabase() {
        log.debug(String.format("[%s]:Populating database from json file", ENTITY_NAME));
        try {
            //Loading json file as a string & making label property consistent
            String json = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filePath)).getPath())));
            //Normalizing json
            JsonArray productsArray = normalizeJson(json);
            //Mapping objects from json array according to product data model
            List<Product> allProducts = new ObjectMapper().readValue(productsArray.toString(),
                    new TypeReference<List<Product>>() {
                    });

            productRepository.saveAll(allProducts);

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    private JsonArray normalizeJson(String json) {
        JsonObject jsonObject = (JsonObject) JsonParser.parseString(json);
        JsonArray productsArray = new JsonArray();

        jsonObject.keySet().iterator().forEachRemaining((product) -> {
            JsonObject productObj;
            JsonObject currentObj = (JsonObject) jsonObject.get(product);
            JsonObject currentObjColors = (JsonObject) currentObj.get(COLORS);
            JsonArray colorsArray = new JsonArray();

            currentObjColors.keySet().iterator().forEachRemaining((color) -> {
                colorsArray.add(currentObjColors.get(color));
            });

            if (colorsArray.size() > 0) {

                for (JsonElement obj : colorsArray) {
                    JsonObject currentColor = (JsonObject) obj;
                    productObj = new JsonObject();
                    productObj.add(LABEL, currentObj.get(LABEL));
                    productObj.add(TYPE, currentObj.get(TYPE));
                    productObj.add(COLOR, determineLabel(currentColor));
                    productObj.add(HEX, currentColor.get(HEX));
                    productObj.add(PRICE, currentColor.get(PRICE));
                    productObj.add(CURRENCY, currentColor.get(CURRENCY));
                    productObj.add(SRC, currentColor.get(SRC));
                    productObj.add(CODE, currentColor.get(CODE));
                    productsArray.add(productObj);
                }
            } else {
                productObj = new JsonObject();
                productObj.add(LABEL, currentObj.get(LABEL));
                productObj.add(TYPE, currentObj.get(TYPE));
                productsArray.add(productObj);
            }
        });

        return productsArray;
    }

    private JsonElement determineLabel(JsonObject currentColor) {
        if (!currentColor.has(WRONG_LABLE)) {
            return currentColor.get(LABEL);
        } else {
            return currentColor.get(WRONG_LABLE);
        }
    }

}
