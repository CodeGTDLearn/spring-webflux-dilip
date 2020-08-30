package com.reactive.spring.repo;

import com.reactive.spring.entities.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReactiveRepoMongo extends ReactiveMongoRepository<Item, Integer> {
}
