package com.reactive.spring.repo;

import com.reactive.spring.entities.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ItemRepo extends ReactiveMongoRepository<Item, String> {

    Mono<Item> findByDescription(String description);
}
