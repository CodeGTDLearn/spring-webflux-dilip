package com.reactive.spring.exceptions;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.reactive.spring.config.MappingsHandler.VERS_FUNCT_ENDPT_EXCEPT;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class Exceptions_Handler {
    @Autowired
    WebTestClient client;
    @Test
    public void isEqual() {
        client
                .get()
                .uri(VERS_FUNCT_ENDPT_EXCEPT)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("RuntimeException Ocurred - Functional Handler");
    }

    @Test
    public void JSONpath() {
        client
                .get()
                .uri(VERS_FUNCT_ENDPT_EXCEPT)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message","RuntimeException Ocurred - Functional Handler");
    }

    @Test
    public void RA() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(client)

                .when()
                .get(VERS_FUNCT_ENDPT_EXCEPT)

                .then()
                .statusCode(INTERNAL_SERVER_ERROR.value())

                .body(equalTo("RuntimeException Ocurred - Functional Handler"))
        ;
    }
}