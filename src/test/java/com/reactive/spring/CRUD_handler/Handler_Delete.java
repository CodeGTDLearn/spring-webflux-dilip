package com.reactive.spring.CRUD_handler;

import com.github.javafaker.Faker;
import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemRepo;
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
public class Handler_Delete {

    @Autowired
    WebTestClient client;

    private List<Item> itemList;
    private Item itemTest;

    @Autowired
    ItemRepo repo;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;

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
    public void webTestClient() {
        client
                .delete()
                .uri(VERS_FUNCT_ENDPT_ID,itemTest.getId())
                .accept(MTYPE_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Void.class);
    }

    @Test
    public void RA() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(client)

                .when()
                .delete(VERS_FUNCT_ENDPT_ID,itemTest.getId())

                .then()
                .statusCode(OK.value())
        ;
    }
}