package com.reactive.spring.controller.v1;

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
public class ItemController {

    @Autowired
    ItemReactiveRepoMongo repo;

    @GetMapping(REQ_MAP)
    public Flux<Item> getAllItems() {
        return repo.findAll();
    }

    @GetMapping(REQ_MAP + ID_PATH)
    public Mono<ResponseEntity<Item>> getById(@PathVariable String id) {
        return repo
                .findById(id)
                .map((item) -> new ResponseEntity<>(item,OK))
                .defaultIfEmpty(new ResponseEntity<>(NOT_FOUND));
    }

    @DeleteMapping(REQ_MAP + ID_PATH)
    public Mono<Void> delete(@PathVariable String id) {
        return repo.deleteById(id);
    }

    @PostMapping(REQ_MAP)
    @ResponseStatus(CREATED)
    public Mono<Item> save(@RequestBody Item item) {
        return repo.save(item);
    }

    @PutMapping(REQ_MAP + ID_PATH)
    public Mono<ResponseEntity<Item>> update(@PathVariable String id,@RequestBody Item item) {
        return repo
                .findById(id)
                .flatMap((currentItem) -> {
                    currentItem.setPrice(item.getPrice());
                    currentItem.setDescription(item.getDescription());
                    return repo.save(currentItem);
                })
                .map(updatedItem -> new ResponseEntity<>(updatedItem, OK))
                .defaultIfEmpty(new ResponseEntity<>(NOT_FOUND));
    }


}
