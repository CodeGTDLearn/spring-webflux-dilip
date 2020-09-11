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

import java.time.Duration;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "10000")
public class Exceptions_Controller {
    @Autowired
    WebTestClient client;



    @Test
    public void isEqual() {
        client
                .get()
                .uri(VERSION + REQ_MAP + EXCEPTION)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("RuntimeException Occured!");
    }

    @Test
    public void JSONpath() {
        client
                .get()
                .uri(VERSION + REQ_MAP + EXCEPTION)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message","RuntimeException Occured!");
    }

    @Test
    public void RA() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(client)

                .when()
                .get(VERSION + REQ_MAP + EXCEPTION)

                .then()
                .statusCode(INTERNAL_SERVER_ERROR.value())

                .body("developerMensagem" ,equalTo("RuntimeException Occured!"))
        ;
    }
}