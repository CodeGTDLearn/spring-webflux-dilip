package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class Save {

    @Autowired
    ItemService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<Item> save(@RequestBody Item item) {
        return service.save(item);
    }
}
