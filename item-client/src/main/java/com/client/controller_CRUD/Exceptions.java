package com.client.controller_CRUD;

import com.client.entity.ItemClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.client.config.MappingsClient.*;

@RestController
@RequestMapping(ROOT)
@Slf4j
public class Exceptions {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping(RETRIEVE + EXCEPTION)
    public Flux<ItemClient> exception_Retrieve() {

        //curl http://localhost:8081/client/retrieve/exception
        return webClient
                .get()
                .uri(API_VERS_FUNC_ENDPT_EXCEPTION)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    Mono<String> errorMono = clientResponse.bodyToMono(String.class);
                    return errorMono.flatMap((errorMessage) -> {
                        log.error("The error message is: " + errorMessage);
                        throw new RuntimeException(errorMessage);
                    });
                })
                .bodyToFlux(ItemClient.class)
                .log("Exception - Client - RETRIEVE: ");
    }


    @GetMapping(EXCHANGE + EXCEPTION)
    public Flux<ItemClient> exception_Exchange() {

        //curl http://localhost:8081/client/exchange/exception
        return webClient
                .get()
                .uri(API_VERS_FUNC_ENDPT_EXCEPTION)
                .exchange()
                .flatMapMany(clientResponse -> {
                    if (clientResponse.statusCode()
                                      .is5xxServerError()) {
                        return clientResponse.bodyToMono(String.class)
                                             .flatMap(errorMessage -> {
                                                 log.error("The error message is: " + errorMessage);
                                                 throw new RuntimeException(errorMessage);
                                             });
                    } else {
                        return clientResponse.bodyToFlux(ItemClient.class);
                    }
                })
                .log("Exception - Client - EXCHANGE: ");
    }

}
