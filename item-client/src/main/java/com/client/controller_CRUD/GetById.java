package com.client.controller_CRUD;

import com.client.entity.ItemClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.client.config.MappingsClient.*;

@RestController
@RequestMapping(ROOT)
public class GetById {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping(RETRIEVE + SINGLE_ITEM)
    public Mono<ItemClient> getById_Retrieve() {

        String id = "5f591ccd678b82570500d8ec";

        return webClient
                .get()
                .uri(API_VERS_FUNC_ENDPT_ID, id)
                .retrieve()
                .bodyToMono(ItemClient.class)
                .log("GetById - RETRIEVE: ");
    }


    @GetMapping(EXCHANGE + SINGLE_ITEM)
    public Mono<ItemClient> getById_Exchange() {

        String id = "5f591ccd678b82570500d8ec";

        return webClient
                .get()
                .uri(API_VERS_FUNC_ENDPT_ID, id)
                .exchange()
                .flatMap(itemResponse -> itemResponse.bodyToMono(ItemClient.class))
                .log("GetById - EXCHANGE: ");
    }

}
