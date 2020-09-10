package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsController_v1_CRUD.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class GetById {

    //    @Autowired
    //    ItemReactiveRepoMongo repo;

    @Autowired
    ItemService service;

    @GetMapping(ID_PATH)
    public Mono<ResponseEntity<Item>> getById(String id) {
        return service
                .getById(id)
                .map((item) -> new ResponseEntity<>(item,OK))
                .switchIfEmpty(ItemNotFoundException());
    }

    public <T> Mono<T> ItemNotFoundException() {
        return Mono.error(new ResponseStatusException(NOT_FOUND,"Item Not found"));
    }

//    private void descriptionNullOrEmptyException(Item item) {
//        if (StringUtil.isNullOrEmpty(item.getDescription()))
//            throw new ResponseStatusException(BAD_REQUEST,"Invalid Description");
//    }
}
