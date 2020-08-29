package com.reactive.spring.service;

import com.reactive.spring.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ItemServicemR2dbc {

    @Autowired
    private ItemServicemR2dbc repo;


    public Flux<Item> findAll() {
        return repo.findAll();
    }


}
