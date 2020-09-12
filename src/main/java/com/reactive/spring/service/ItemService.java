package com.reactive.spring.service;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ItemService {

    @Autowired
    private ItemRepo repo;

    public Flux<Item> getAll() {
        return repo.findAll();
    }

    public Mono<Item> save(Item item) {
        return repo.save(item);
    }

    public Mono<Item> getById(String id) {
        return repo
                .findById(id)
                .map((item) -> item)
                .switchIfEmpty(ItemNotFoundException());
    }

    public Mono<Void> delete(String id) {
        return repo
                .findById(id)
                .switchIfEmpty(ItemNotFoundException())
                .flatMap(repo::delete);
    }

    public Mono<Item> update(String id, Item item) {
        return repo
                .findById(id)
                .switchIfEmpty(ItemNotFoundException())
                .flatMap((foundItem) -> {
                    foundItem.setPrice(item.getPrice());
                    foundItem.setDescription(item.getDescription());
                    return repo.save(foundItem);
                });
    }

    public <T> Mono<T> ItemNotFoundException() {
        return Mono.error(new ResponseStatusException(NOT_FOUND,"Item Not found"));
    }
}
