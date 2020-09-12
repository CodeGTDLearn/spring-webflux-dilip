package com.reactive.spring.exceptions;

import com.github.javafaker.Faker;
import com.reactive.spring.entities.Item;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
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

    private Item itemTest;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;
    final ContentType CONT_ANY = ContentType.ANY;
    final ContentType CONT_JSON = ContentType.JSON;

    @Before
    public void setUpLocal() {
        var ItemTestId = Faker.instance()
                              .idNumber()
                              .valid();

        itemTest = newItemWithIdDescPrice(ItemTestId).create();
    }

    @Test
    public void Delete_404_JSONpath() {

        client
                .put()
                .uri(VERSION + REQ_MAP + ID_PATH,itemTest.getId())
                .contentType(MTYPE_JSON)
                .accept(MTYPE_JSON)
                .body(Mono.just(itemTest),Item.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void GetById_404_RA() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(client)
                .header("Accept",CONT_ANY)
                .header("Content-type",CONT_JSON)
                .body(itemTest)

                .when()
                .get(VERSION + REQ_MAP + ID_PATH,Faker.instance().idNumber().valid())

                .then()
                .statusCode(NOT_FOUND.value())

                .body("developerMensagem" ,equalTo("Custom Attrib - An Error Happens!"))
        ;
    }

    @Test
    public void Update_404_RA() {
        itemTest.setPrice(Faker.instance()
                               .random()
                               .nextDouble());

        RestAssuredWebTestClient
                .given()
                .webTestClient(client)
                .header("Accept",CONT_ANY)
                .header("Content-type",CONT_JSON)
                .body(itemTest)

                .when()
                .put(VERSION + REQ_MAP + ID_PATH,Faker.instance().idNumber().valid())

                .then()
                .body("developerMensagem" ,equalTo("Custom Attrib - An Error Happens!"))
        ;
    }
}