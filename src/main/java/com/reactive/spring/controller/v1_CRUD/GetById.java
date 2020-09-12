package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static org.springframework.http.HttpStatus.OK;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class GetById {

    @Autowired
    ItemService service;

    @GetMapping(ID_PATH)
    public Mono<ResponseEntity<Item>> getById(@PathVariable String id) {
        return service
                .getById(id)
                .map((item) -> new ResponseEntity<>(item,OK));
    }

}
