package com.client.controller_CRUD;

import com.client.entity.ItemClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.client.config.MappingsClient.*;

@RestController
@RequestMapping(ROOT)
public class Save {

    WebClient webClient = WebClient.create("http://localhost:8080");

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;

    @PostMapping(RETRIEVE)
    public Mono<ItemClient> save_Retrieve(@RequestBody ItemClient item) {
        Mono<ItemClient> itemMono = Mono.just(item);

        return webClient
                .post()
                .uri(API_VERS_FUNC_ENDPT)
                .contentType(MTYPE_JSON)
                .body(itemMono,ItemClient.class)
                .retrieve()
                .bodyToMono(ItemClient.class)
                .log("Saved - RETRIEVE:");
    }


    @PostMapping(EXCHANGE)
    public Mono<ItemClient> save_Exchange(@RequestBody ItemClient item) {

        Mono<ItemClient> itemMono = Mono.just(item);

        return webClient
                .post()
                .uri(API_VERS_FUNC_ENDPT)
                .contentType(MTYPE_JSON)
                .body(itemMono,ItemClient.class)
                .exchange()
                .flatMap(itemResponse -> itemResponse.bodyToMono(ItemClient.class))
                .log("save - EXCHANGE: ");
    }
}
