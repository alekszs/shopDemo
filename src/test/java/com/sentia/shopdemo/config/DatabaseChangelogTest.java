package com.sentia.shopdemo.config;

import com.sentia.shopdemo.ShopdemoApplication;
import com.sentia.shopdemo.model.Product;
import com.sentia.shopdemo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ShopdemoApplication.class})
@ActiveProfiles("test")
class DatabaseChangelogTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void validateNumberOfProducts() {
        assertEquals(2015, productRepository.findAll().size());
    }

    @Test
    void validateCertainProducts() {
        List<Product> testProducts1 = productRepository.findAllByLabelAndType("Fintone","Latlux");
        List<Product> testProducts2 = productRepository.findAllByLabelAndType("Viva", "Aerified");

        Product testProduct1A = testProducts1.get(0);
        Product testProduct1M = testProducts1.get(1);

        assertEquals(2, testProducts1.size());
        assertEquals(1, testProducts2.size());

        assertEquals("Fintone", testProduct1A.getLabel());
        assertEquals("Latlux", testProduct1A.getType());
        assertEquals("Aquamarine", testProduct1A.getColor());
        assertEquals("#4c211a", testProduct1A.getHex());
        assertEquals(52.38, testProduct1A.getPrice());
        assertEquals("Ruble", testProduct1A.getCurrency());
        assertEquals("https://api.sentia.com/145x100.png/ff4444/ffffff", testProduct1A.getSrc());
        assertEquals("1940723124", testProduct1A.getCode());

        assertEquals("Fintone", testProduct1M.getLabel());
        assertEquals("Latlux", testProduct1M.getType());
        assertEquals("Mauv", testProduct1M.getColor());
        assertEquals("#c71e77", testProduct1M.getHex());
        assertEquals(66.66, testProduct1M.getPrice());
        assertEquals("Zloty", testProduct1M.getCurrency());
        assertEquals("https://api.sentia.com/246x100.png/ff4444/ffffff", testProduct1M.getSrc());
        assertEquals("9900056965", testProduct1M.getCode());

        assertEquals("Viva", testProducts2.get(0).getLabel());
        assertEquals("Aerified", testProducts2.get(0).getType());
    }

}