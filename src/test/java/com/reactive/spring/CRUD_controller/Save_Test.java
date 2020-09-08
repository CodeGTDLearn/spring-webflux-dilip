package com.reactive.spring.CRUD_controller;


import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
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

import java.util.Arrays;
import java.util.List;

import static com.reactive.spring.config.Mappings_Controller.REQ_MAP;
import static com.reactive.spring.config.Mappings_Controller.VERSION;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class Save_Test {

    @Autowired
    WebTestClient webTestClient;

    private List<Item> itemList;
    private Item itemTest;

    @Autowired
    ItemReactiveRepoMongo repo;

    @Before
    public void setUpLocal() {

        itemTest = newItemWithIdDescPrice("ABC").create();

        itemList = Arrays.asList(newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 itemTest
                                );

        repo.deleteAll()
            .thenMany(Flux.fromIterable(itemList))
            .flatMap(repo::save)
            .doOnNext((item -> System.out.println("Inserted item is - TEST: " + item)))
            .blockLast(); // THATS THE WHY, BLOCKHOUND IS NOT BEING USED.
    }

    @Test
    public void saveItem() {
        webTestClient
                .post()
                .uri(VERSION + REQ_MAP)
                .body(Mono.just(itemTest),Item.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(itemTest.getId())
                .jsonPath("$.price")
                .isEqualTo(itemTest.getPrice())
                .jsonPath("$.description")
                .isEqualTo(itemTest.getDescription())
        ;
    }

    @Test
    public void saveItem_RestAssuredWebTestClient() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(webTestClient)
                .header("Accept",ContentType.ANY)
                .header("Content-type",ContentType.JSON)
                .body(itemTest)

                .when()
                .post(VERSION + REQ_MAP)

                .then()
                .log()
                .headers()
                .and()
                .log()
                .body()
                .and()
                .contentType(ContentType.JSON)
                .statusCode(CREATED.value())

                //equalTo para o corpo do Json
                .body("description", containsString(itemTest.getDescription()));
    }
}
