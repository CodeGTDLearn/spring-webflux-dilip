package com.reactive.spring.CRUD_controller;


import com.github.javafaker.Faker;
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

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.reactive.spring.config.Mappings_Controller.*;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class Update_Test {

    @Autowired
    WebTestClient webTestClient;

    private List<Item> itemList;
    private Item itemTest;

    @Autowired
    ItemReactiveRepoMongo repo;

    @Before
    public void setUpLocal() {
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofMillis(75000L))
                .build();

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
    public void update_jsonPath() {
        itemTest.setPrice(Faker.instance()
                               .random()
                               .nextDouble());
        webTestClient
                .put()
                .uri(VERSION + REQ_MAP + ID_PATH,itemTest.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(itemTest),Item.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.price",itemTest.getPrice());
    }

    @Test
    public void update_jsonPath_notfound() {
        webTestClient
                .put()
                .uri(VERSION + REQ_MAP + ID_PATH,Faker.instance()
                                                      .random()
                                                      .hex())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(itemTest),Item.class)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void update_RestAssuredWebTestClient() {
        itemTest.setPrice(Faker.instance()
                               .random()
                               .nextDouble());

        RestAssuredWebTestClient
                .given()
                .webTestClient(webTestClient)
                .header("Accept",ContentType.ANY)
                .header("Content-type",ContentType.JSON)
                .body(itemTest)

                .when()
                .put(VERSION + REQ_MAP + ID_PATH,itemTest.getId())

                .then()
                .log()
                .headers()
                .and()
                .log()
                .body()
                .and()
                .statusCode(OK.value())
        ;
    }
}

