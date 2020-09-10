package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsControllerV1.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@Slf4j
@RequestMapping(value = VERSION)
//@AllArgsConstructor
public class Save {

    @Autowired
    ItemReactiveRepoMongo repo;

    @PostMapping(REQ_MAP)
    @ResponseStatus(CREATED)
    public Mono<Item> save(@RequestBody Item item) {
        return repo.save(item);
    }
}
