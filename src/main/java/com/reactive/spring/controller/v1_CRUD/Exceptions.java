package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;

@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class Exceptions {

//    @Autowired
//    ItemRepo repo;

    @Autowired
    ItemService service;

    @GetMapping(EXCEPTION)
    public Flux<Item> runTimeException() {
        return service
                .getAll()
                .concatWith(
                        Mono.error(new RuntimeException("RuntimeException Occured!")));
    }
}
