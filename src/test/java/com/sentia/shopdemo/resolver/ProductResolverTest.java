package com.sentia.shopdemo.resolver;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.sentia.shopdemo.ShopdemoApplication;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ShopdemoApplication.class})
@ActiveProfiles("test")
class ProductResolverTest {

    private static final String GRAPHQL_QUERY_REQUEST_PATH = "graphql/resolver/query/request/%s.graphql";
    private static final String GRAPHQL_QUERY_RESPONSE_PATH = "graphql/resolver/query/response/%s.json";

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void getProductsFilteredByLabel() throws Exception {
        var testName = "product_label";
        var graphQLResponse = graphQLTestTemplate
                .postForResource(format(GRAPHQL_QUERY_REQUEST_PATH, testName));

        var expectedResponseBody = read(format(GRAPHQL_QUERY_RESPONSE_PATH, testName));

        assertThat(graphQLResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, graphQLResponse.getRawResponse().getBody(), true);
    }

    @Test
    void getProductsFilteredByPriceRange() throws Exception {
        var testName = "product_price";
        var graphQLResponse = graphQLTestTemplate
                .postForResource(format(GRAPHQL_QUERY_REQUEST_PATH, testName));

        var expectedResponseBody = read(format(GRAPHQL_QUERY_RESPONSE_PATH, testName));

        assertThat(graphQLResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, graphQLResponse.getRawResponse().getBody(), true);
    }

    @Test
    void getProductsFilteredByTypeAndSortedByPrice() throws Exception {
        var testName = "product_type_sort";
        var graphQLResponse = graphQLTestTemplate
                .postForResource(format(GRAPHQL_QUERY_REQUEST_PATH, testName));

        var expectedResponseBody = read(format(GRAPHQL_QUERY_RESPONSE_PATH, testName));

        assertThat(graphQLResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, graphQLResponse.getRawResponse().getBody(), true);
    }

    private String read(String location) throws IOException {
        return IOUtils.toString(new ClassPathResource(location).getInputStream(), StandardCharsets.UTF_8);
    }

}