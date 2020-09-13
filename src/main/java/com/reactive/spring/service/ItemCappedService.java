package com.reactive.spring.service;

import com.reactive.spring.entities.ItemCapped;
import com.reactive.spring.repo.ItemRepoCapped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ItemCappedService {

    @Autowired
    private ItemRepoCapped repo;

    public Flux<ItemCapped> findItemsBy() {
        return repo.findItemsBy();
    }
}
