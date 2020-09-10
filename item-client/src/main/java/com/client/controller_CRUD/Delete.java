package com.client.controller_CRUD;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.client.config.MappingsClient.*;

@RestController
@RequestMapping(ROOT)

public class Delete {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @DeleteMapping(RETRIEVE + ID_PATH)
    public Mono<Void> delete_Retrieve(@PathVariable String id) {
        return webClient
                .delete()
                .uri(API_VERS_FUNC_ENDPT_ID, id)
                .retrieve()
                .bodyToMono(Void.class)
                .log("Delete - RETRIEVE: ");
    }

    @DeleteMapping(EXCHANGE + ID_PATH)
    public Mono<Void> delete_Exchange(@PathVariable String id) {
        return webClient
                .delete()
                .uri(API_VERS_FUNC_ENDPT_ID, id)
                .exchange()
                .flatMap(itemResponse -> itemResponse.bodyToMono(Void.class))
                .log("Delete - EXCHANGE: ");
    }
}

