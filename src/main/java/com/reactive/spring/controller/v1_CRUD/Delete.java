package com.reactive.spring.controller.v1_CRUD;

import com.reactive.spring.repo.ItemReactiveRepoMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsControllerV1.*;


@RestController
@Slf4j
@RequestMapping(value = VERSION)
//@AllArgsConstructor
public class Delete {

    @Autowired
    ItemReactiveRepoMongo repo;

    @DeleteMapping(REQ_MAP + ID_PATH)
    public Mono<Void> delete(@PathVariable String id) {
        return repo.deleteById(id);
    }

}
