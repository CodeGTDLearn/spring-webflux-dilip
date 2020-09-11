package com.reactive.spring.CRUD_handler;

import com.github.javafaker.Faker;
import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemRepo;
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

import static com.reactive.spring.config.MappingsHandler.VERS_FUNCT_ENDPT_ID;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class Handler_Update {

    @Autowired
    WebTestClient client;

    private List<Item> itemList;
    private Item itemTest;

    @Autowired
    ItemRepo repo;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;
    final ContentType CONT_ANY = ContentType.ANY;
    final ContentType CONT_JSON = ContentType.JSON;


    @Before
    public void setUpLocal() {

        client = client
                .mutate()
                .responseTimeout(Duration.ofMillis(75000L))
                .build();

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
    public void jsonPath() {
        itemTest.setPrice(Faker.instance()
                               .random()
                               .nextDouble());
        client
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
    public void jsonPath_notfound() {
        client
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
    public void RA() {
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