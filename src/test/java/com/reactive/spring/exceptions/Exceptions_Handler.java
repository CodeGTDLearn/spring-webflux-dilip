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
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static com.reactive.spring.config.MappingsHandler.VERS_FUNCT_ENDPT_EXCEPT;
import static com.reactive.spring.config.MappingsHandler.VERS_FUNCT_ENDPT_ID;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class Exceptions_Handler {
    @Autowired
    WebTestClient client;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;
    final ContentType CONT_ANY = ContentType.ANY;
    final ContentType CONT_JSON = ContentType.JSON;

    private Item itemTest;

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
                .uri(VERS_FUNCT_ENDPT_ID,itemTest.getId())
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
                .get(VERS_FUNCT_ENDPT_ID,Faker.instance().idNumber().valid())

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
                .put(VERS_FUNCT_ENDPT_ID,Faker.instance().idNumber().valid())

                .then()
                .body("developerMensagem" ,equalTo("Custom Attrib - An Error Happens!"))
        ;
    }
}