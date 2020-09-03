package com.reactive.spring.controller.CRUD;


import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
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

import java.util.Arrays;
import java.util.List;

import static com.reactive.spring.config.Mappings.*;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ItemController_GetById_Test {

    @Autowired
    WebTestClient webTestClient;

    private List<Item> itemList;
    private Item item;

    @Autowired
    ItemReactiveRepoMongo repo;

    @Before
    public void setUpLocal() {

        item = newItemWithIdDescPrice("ABC").create();

        itemList = Arrays.asList(newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 item
                                );

        repo.deleteAll()
            .thenMany(Flux.fromIterable(itemList))
            .flatMap(repo::save)
            .doOnNext((item -> System.out.println("Inserted item is - TEST: " + item)))
            .blockLast(); // THATS THE WHY, BLOCKHOUND IS NOT BEING USED.
    }

    @Test
    public void getById_jsonPath() {
        webTestClient
                .get()
                .uri(VERSION + GET_ENDPOINT + ID_PATH,item.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.price",item.getPrice());
    }

    @Test
    public void getById_jsonPath_notfound() {
        webTestClient
                .get()
                .uri(VERSION + GET_ENDPOINT + ID_PATH,"DEF")
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    public void getById_RestAssuredWebTestClient() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(webTestClient)

                .when()
                .get(VERSION + GET_ENDPOINT + ID_PATH,item.getId())

                .then()
                .statusCode(OK.value())

                .body("description",is(item.getDescription()))
        ;
    }
}
