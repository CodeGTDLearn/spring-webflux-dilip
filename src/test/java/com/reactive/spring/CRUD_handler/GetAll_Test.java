package com.reactive.spring.CRUD_handler;

import com.github.javafaker.Faker;
import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
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
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static com.reactive.spring.config.Mappings_Controller.*;
import static com.reactive.spring.config.Mappings_Handler.VERS_FUNCT_ENDPT;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class GetAll_Test {

    @Autowired
    WebTestClient webTestClient;

    private List<Item> itemList;
    private Item itemTest;

    @Autowired
    ItemReactiveRepoMongo repo;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;
    final ContentType CONT_ANY = ContentType.ANY;
    final ContentType CONT_JSON = ContentType.JSON;

    @Before
    public void setUpLocal() {
        var ItemTestId = Faker.instance()
                              .idNumber()
                              .valid();

        itemTest = newItemWithIdDescPrice(ItemTestId).create();

        itemList = Arrays.asList(newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 itemTest
                                );

        repo.deleteAll()
            .thenMany(Flux.fromIterable(itemList))
            .flatMap(repo::save)
            .doOnNext((item -> System.out.println("Inserted - ItemHandlerTEST: " + item)))
            .blockLast(); // THATS THE WHY, BLOCKHOUND IS NOT BEING USED.
    }

    @Test
    public void getAll_HasSize() {
        webTestClient
                .get()
                .uri(VERS_FUNCT_ENDPT)
                .exchange()
                .expectHeader()
                .contentType(MTYPE_JSON)
                .expectBodyList(Item.class)
                .hasSize(4);
    }

    @Test
    public void getAll_ConsumesWith() {
        webTestClient
                .get()
                .uri(VERS_FUNCT_ENDPT)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MTYPE_JSON)
                .expectBodyList(Item.class)
                .hasSize(4)
                .consumeWith((response) -> {
                    List<Item> listItems = response.getResponseBody();
                    listItems.forEach((item) -> assertTrue(item.getId() != null));
                });
    }

    @Test
    public void getAll_StepVerifier() {
        Flux<Item> itemFlux =
                webTestClient
                        .get()
                        .uri(VERS_FUNCT_ENDPT)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectHeader()
                        .contentType(MTYPE_JSON)
                        .returnResult(Item.class)
                        .getResponseBody();

        StepVerifier
                .create(itemFlux.log("Value from Network - StepVerifier: "))
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void getAll_RestAssuredWebTestClient() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(webTestClient)
                .header("Accept",CONT_ANY)
                .header("Content-type",CONT_JSON)

                .when()
                .get(VERS_FUNCT_ENDPT)

                .then()
                .statusCode(OK.value())
                .log()
                .headers()
                .and()
                .log()
                .body()
                .and()

                .body("id",hasItem(itemTest.getId()))
                .body("description",hasItem(itemTest.getDescription()))
                .body("id",hasItem(itemList.get(0)
                                           .getId()))
                .body("id",hasItem(itemList.get(1)
                                           .getId()))
                .body("id",hasItem(itemList.get(2)
                                           .getId()))
        ;
    }
}