package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class Delete {

    //    @Autowired
    //    ItemRepo repo;

    @Autowired
    ItemService service;

    @DeleteMapping(ID_PATH)
    public Mono<Void> delete(@PathVariable String id) {
        return service.delete(id);
    }

}
