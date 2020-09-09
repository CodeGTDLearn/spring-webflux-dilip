package com.client.controller_CRUD;

import com.client.entity.ItemClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static com.client.config.Mappings.*;

@RestController
@RequestMapping(ROOT)
public class GetAll {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping(RETRIEVE)
    public Flux<ItemClient> getAll_Retrieve (){
        return webClient
                .get()
                .uri(API_VERS_FUNC_ENDPT)
                .retrieve()
                .bodyToFlux(ItemClient.class)
                .log("GetAll - RETRIEVE: ");
    }


    @GetMapping(EXCHANGE)
    public Flux<ItemClient> getAll_Exchange (){
        return webClient
                .get()
                .uri(API_VERS_FUNC_ENDPT)
                .exchange()
                .flatMapMany(itemResponse -> itemResponse.bodyToFlux(ItemClient.class))
                .log("GetAll - EXCHANGE: ");
    }

}
