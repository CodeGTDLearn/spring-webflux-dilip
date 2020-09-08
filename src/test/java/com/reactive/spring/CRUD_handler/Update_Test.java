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
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static com.reactive.spring.config.Mappings_Controller.*;
import static com.reactive.spring.config.Mappings_Handler.VERS_FUNCT_ENDPT_ID;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.hamcrest.Matchers.is;
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
    public void update_jsonPath() {
        itemTest.setPrice(Faker.instance()
                               .random()
                               .nextDouble());
        webTestClient
                .put()
                .uri(VERS_FUNCT_ENDPT_ID,itemTest.getId())
                .contentType(MTYPE_JSON)
                .accept(MTYPE_JSON)
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
                .uri(VERS_FUNCT_ENDPT_ID,Faker.instance()
                                                      .random()
                                                      .hex())
                .contentType(MTYPE_JSON)
                .accept(MTYPE_JSON)
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
                .header("Accept",CONT_ANY)
                .header("Content-type",CONT_JSON)
                .body(itemTest)

                .when()
                .put(VERS_FUNCT_ENDPT_ID,itemTest.getId())

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