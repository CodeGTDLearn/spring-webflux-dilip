package com.reactive.spring.handler.CRUD;

import com.reactive.spring.entities.Item;
import com.reactive.spring.entities.ItemCapped;
import com.reactive.spring.service.ItemCappedService;
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
    ItemService itemService;

    @Autowired
    ItemCappedService cappedService;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;
    final MediaType MTYPE_STREAM_JSON = MediaType.APPLICATION_STREAM_JSON;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ok()
                .contentType(MTYPE_JSON)
                .body(itemService.getAll(),Item.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable(ID);
        Mono<Item> itemFound = itemService.getById(id);

        return itemFound.flatMap(item -> ok().contentType(MTYPE_JSON)
                                             .body(fromValue(item)))
                        .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable(ID);

        Mono<Void> deletedItem = itemService.delete(id);

        return ServerResponse.ok()
                             .contentType(MTYPE_JSON)
                             .body(deletedItem,Void.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable(ID);

        Mono<Item> updatedItem =
                request.bodyToMono(Item.class)
                       .flatMap((requestBody -> itemService.update(id,requestBody)));

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
                        .body(itemService.save(item),Item.class));
    }

    public Mono<ServerResponse> except(ServerRequest request) {
        throw new RuntimeException("RuntimeException Ocurred - Functional Handler");
    }

    public Mono<ServerResponse> itemStream(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MTYPE_STREAM_JSON)
                .body(cappedService.findItemsBy(),
                      ItemCapped.class
                     );
    }

}
