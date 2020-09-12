package com.reactive.spring.handler.CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.service.ItemService;
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
    ItemService service;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok()
                .contentType(MTYPE_JSON)
                .body(service.getAll(),Item.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable(ID);
        Mono<Item> itemFound = service.getById(id);

        return itemFound.flatMap(item -> ok().contentType(MTYPE_JSON)
                                             .body(fromValue(item)))
                        .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable(ID);

        Mono<Void> deletedItem = service.delete(id);

        return ServerResponse.ok()
                             .contentType(MTYPE_JSON)
                             .body(deletedItem,Void.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable(ID);

        Mono<Item> updatedItem =
                request.bodyToMono(Item.class)
                       .flatMap((requestBody -> service.update(id,requestBody)));

        return updatedItem
                .flatMap(item -> ok()
                        .contentType(MTYPE_JSON)
                        .body(fromValue(item)));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Item> itemToBeInserted = request.bodyToMono(Item.class);

        return itemToBeInserted
                .flatMap(item -> ok()
                        .contentType(MTYPE_JSON)
                        .body(service.save(item),Item.class));
    }

    public Mono<ServerResponse> except(ServerRequest request) {
        throw new RuntimeException("RuntimeException Ocurred - Functional Handler");
    }
}
