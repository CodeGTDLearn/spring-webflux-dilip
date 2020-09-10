package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class GetAll {

//    @Autowired
//    ItemReactiveRepoMongo repo;

    @Autowired
    ItemService service;

    @GetMapping
    public Flux<Item> getAllItems() {
        return service.getAll();
    }
}
