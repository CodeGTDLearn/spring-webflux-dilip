package com.client.controller_CRUD;

import com.client.entity.ItemClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.client.config.MappingsClient.*;

@RestController
@RequestMapping(ROOT)
public class Update {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @PutMapping(RETRIEVE + ID_PATH)
    public Mono<ItemClient> update_Retrieve(@PathVariable String id,
                                            @RequestBody ItemClient item) {

        Mono<ItemClient> itemMono = Mono.just(item);

        return webClient
                .put()
                .uri(API_VERS_FUNC_ENDPT_ID, id)
                .body(itemMono,ItemClient.class)
                .retrieve()
                .bodyToMono(ItemClient.class)
                .log("Updated - RETRIEVE:");
    }

    @PutMapping(EXCHANGE + ID_PATH)
    public Mono<ItemClient> update_Exchange(@PathVariable String id,
                                            @RequestBody ItemClient item) {

        Mono<ItemClient> itemMono = Mono.just(item);

        return webClient
                .put()
                .uri(API_VERS_FUNC_ENDPT_ID, id)
                .body(itemMono,ItemClient.class)
                .exchange()
                .flatMap(itemResponse -> itemResponse.bodyToMono(ItemClient.class))
                .log("Updated - EXCHANGE: ");
    }
}
