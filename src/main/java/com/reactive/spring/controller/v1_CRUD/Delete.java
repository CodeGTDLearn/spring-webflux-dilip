package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class Delete {

    @Autowired
    ItemService service;

    @DeleteMapping(ID_PATH)
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

}
