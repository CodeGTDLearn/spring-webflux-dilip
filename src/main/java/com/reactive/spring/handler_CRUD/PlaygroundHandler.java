package com.reactive.spring.handler_CRUD;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
@AllArgsConstructor
public class PlaygroundHandler {

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;

    public Mono<ServerResponse> flux(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MTYPE_JSON)
                .body(
                        Flux.just(1,2,3)
                            .log(),
                        Integer.class
                     );
    }

    public Mono<ServerResponse> mono(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MTYPE_JSON)
                .body(
                        Mono.just(1)
                            .log(),
                        Integer.class
                     );
    }

}
