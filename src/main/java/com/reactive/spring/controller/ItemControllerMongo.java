package com.reactive.spring.controller;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("itemsmongo")
public class ItemControllerMongo {

    @Autowired
    ItemService service;

    @GetMapping(value = "item-flux-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//    @GetMapping
    @ResponseStatus(OK)
    public Flux<Item> findAll(){
        return service.getAll();
    }

}
