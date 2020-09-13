package com.reactive.spring.stream;

import com.reactive.spring.entities.ItemCapped;
import com.reactive.spring.repo.ItemRepoCapped;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static com.reactive.spring.config.MappingsStream.STREAM_ENDPOINT;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class StreamController {

    @Autowired
    ItemRepoCapped repo;

    @Autowired
    ReactiveMongoOperations mongoOps;

    @Autowired
    WebTestClient client;

    @Before
    public void setUpLocal() {
        mongoOps.dropCollection(ItemCapped.class);
        mongoOps.createCollection(
                ItemCapped.class,
                CollectionOptions.empty()
                                 .maxDocuments(20)
                                 .size(50000)
                                 .capped()
                                 ).block();

        Flux<ItemCapped> itemCappedFlux =
                Flux.interval(Duration.ofMillis(10))
                    .map(i -> new ItemCapped(null,
                                             "Random Capped Test " + i,
                                             (100.00 + i)
                    ))
                    .take(5);

        repo.insert(itemCappedFlux)
            .doOnNext((itemCapped) ->
                              System.out.println("Inserted Controller Item Test: " + itemCapped))
            .blockLast();
    }

    @Test
    public void getAll(){
        Flux<ItemCapped> itemCappedFlux =
        client
                .get()
                .uri(STREAM_ENDPOINT)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult((ItemCapped.class))
                .getResponseBody()
                .take(5);

        StepVerifier
                .create(itemCappedFlux)
                .expectNextCount(5)
                .thenCancel()
                .verify();
    }

}