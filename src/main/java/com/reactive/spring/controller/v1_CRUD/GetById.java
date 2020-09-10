package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsControllerV1.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;


@RestController
@Slf4j
@RequestMapping(value = VERSION)
//@AllArgsConstructor
public class GetById {

    @Autowired
    ItemReactiveRepoMongo repo;

    @GetMapping(REQ_MAP + ID_PATH)
    public Mono<ResponseEntity<Item>> getById(@PathVariable String id) {
        return repo
                .findById(id)
                .map((item) -> new ResponseEntity<>(item,OK))
                .defaultIfEmpty(new ResponseEntity<>(NOT_FOUND));
    }

}
