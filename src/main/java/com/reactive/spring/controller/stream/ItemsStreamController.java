package com.reactive.spring.controller.stream;

import com.reactive.spring.entities.ItemCapped;
import com.reactive.spring.service.ItemCappedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.reactive.spring.config.MappingsStream.REQ_MAP;
import static com.reactive.spring.config.MappingsStream.VERSION;
import static org.springframework.http.HttpStatus.OK;



@RestController
@Slf4j
@RequestMapping(VERSION + REQ_MAP)
//@AllArgsConstructor
public class ItemsStreamController {

    @Autowired
    ItemCappedService service;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(OK)
    public Flux<ItemCapped> getAllStream() {
        return service.findItemsBy();
    }
}
