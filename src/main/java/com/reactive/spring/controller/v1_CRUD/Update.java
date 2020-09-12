package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static org.springframework.http.HttpStatus.OK;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class Update {

    @Autowired
    ItemService service;

    @PutMapping(ID_PATH)
    @ResponseStatus(OK)
    public Mono<ResponseEntity<Item>> update(@PathVariable String id,@RequestBody Item item) {
        return service.update(id,item)
                      .map(updatedItem -> new ResponseEntity<>(updatedItem,OK));
    }
}

