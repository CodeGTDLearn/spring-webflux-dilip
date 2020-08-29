//package com.reactive.spring.controller;
//
//import com.reactive.spring.entities.Item;
//import com.reactive.spring.service.ItemServicemMongo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//import static org.springframework.http.HttpStatus.OK;
//
//@RestController
//@RequestMapping("items")
//public class ItemControllerMongo {
//
//    @Autowired
//    ItemServiceMongo service;
//
////    @GetMapping(value = "item-flux-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//    @GetMapping
//    @ResponseStatus(OK)
//    public Flux<Item> findAll(){
//        return service.findAll();
//    }
//
//}
