package com.reactive.spring.handler;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.reactive.spring.config.MappingsHandler.ID;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ItemsHandler {

    @Autowired
    ItemReactiveRepoMongo repo;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok()
                .contentType(MTYPE_JSON)
                .body(repo.findAll(),Item.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable(ID);
        Mono<Item> itemFound = repo.findById(id);

        return itemFound.flatMap(item -> ok().contentType(MTYPE_JSON)
                                             .body(fromValue(item)))
                        .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable(ID);

        Mono<Void> deletedItem = repo.deleteById(id);

        return ServerResponse.ok()
                             .contentType(MTYPE_JSON)
                             .body(deletedItem,Void.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable(ID);

        Mono<Item> updatedItem = request
                .bodyToMono(Item.class)
                .flatMap(item -> {
                    Mono<Item> itemMono = repo
                            .findById(id)
                            .flatMap(currentItem -> {
                                currentItem.setDescription(item.getDescription());
                                currentItem.setPrice(item.getPrice());
                                return repo.save(currentItem);
                            });
                    return itemMono;
                });

        return updatedItem.flatMap(item -> ServerResponse
                .ok()
                .contentType(MTYPE_JSON)
                .body(fromValue(item)))
                          .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Item> itemToBeInserted = request.bodyToMono(Item.class);

        return itemToBeInserted
                .flatMap(item -> ok()
                        .contentType(MTYPE_JSON)
                        .body(repo.save(item),Item.class));
    }

}
