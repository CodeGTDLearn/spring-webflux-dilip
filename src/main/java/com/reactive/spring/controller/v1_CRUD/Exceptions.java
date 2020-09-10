package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsControllerV1.*;

@RestController
@Slf4j
@RequestMapping(VERSION)
//@AllArgsConstructor
public class Exceptions {

    @Autowired
    ItemReactiveRepoMongo repo;

    @GetMapping(REQ_MAP + EXCEPT)
    public Flux<Item> getAllItems() {
        return repo
                .findAll()
                .concatWith(Mono.error(new RuntimeException("runtimeException Occured!")));
    }
}
