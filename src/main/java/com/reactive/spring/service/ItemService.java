package com.reactive.spring.service;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemService {

    @Autowired
    private ItemReactiveRepoMongo repo;

    public Flux<Item> getAll() {
        return repo.findAll();
    }

    public Mono<Item> save(Item item) {
        return repo.save(item);
    }

    public Mono<Item> getById(String id) {
        return repo.findById(id);
    }

    public Mono<Void> delete( String id) {
        return repo.deleteById(id);
    }
}
