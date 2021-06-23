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
    private final String WRONG_RIGHT = "label";
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
            json = json.replaceAll(WRONG_LABLE, WRONG_RIGHT);

            //Normalizing json
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
                        productObj = new JsonObject();
                        JsonObject currentColor = (JsonObject) obj;
                        productObj.add("label", currentObj.get("label"));
                        productObj.add("type", currentObj.get("type"));
                        productObj.add("color", currentColor.get("label"));
                        productObj.add("hex", currentColor.get("hex"));
                        productObj.add("price", currentColor.get("price"));
                        productObj.add("currency", currentColor.get("currency"));
                        productObj.add("src", currentColor.get("src"));
                        productObj.add("code", currentColor.get("code"));
                        productsArray.add(productObj);
                    }
                } else {
                    productObj = new JsonObject();
                    productObj.add("label", currentObj.get("label"));
                    productObj.add("type", currentObj.get("type"));
                    productsArray.add(productObj);
                }
            });

            //Mapping objects from json array according to product data model
            List<Product> allProducts = new ObjectMapper().readValue(productsArray.toString(),
                    new TypeReference<List<Product>>() {
                    });

            productRepository.saveAll(allProducts);

        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
