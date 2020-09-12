package com.reactive.spring.exceptions;

import com.github.javafaker.Faker;
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
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "10000")
public class Exceptions_Controller {
    @Autowired
    WebTestClient client;



    @Test
    public void Delete_404_JSONpath() {
        client
                .delete()
                .uri(VERSION + REQ_MAP + ID_PATH,Faker.instance().idNumber().valid())
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.mensagem","404 NOT_FOUND \"Item Not found\"");
    }

    @Test
    public void GetById_404_RA() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(client)

                .when()
                .get(VERSION + REQ_MAP + ID_PATH,Faker.instance().idNumber().valid())

                .then()
                .statusCode(NOT_FOUND.value())

                .body("developerMensagem" ,equalTo("Custom Attrib - An Error Happens!"))
        ;
    }
}