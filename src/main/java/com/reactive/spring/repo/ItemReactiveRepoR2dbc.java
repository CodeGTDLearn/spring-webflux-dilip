package com.reactive.spring.repo;

import com.reactive.spring.entities.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReactiveRepoR2dbc extends ReactiveCrudRepository<Item, Integer> {
}


