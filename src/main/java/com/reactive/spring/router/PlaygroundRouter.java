package com.reactive.spring.router;

import com.reactive.spring.handler.PlaygroundHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

//@Configuration
public class PlaygroundRouter {


    @Bean
    public RouterFunction<ServerResponse> route(PlaygroundHandler playgroundHandlerFunction) {
        return RouterFunctions
                .route(GET("functional/flux").and(accept(MediaType.APPLICATION_JSON)),
                       playgroundHandlerFunction::flux
                      )
                .andRoute(GET("functional/mono").and(accept(MediaType.APPLICATION_JSON)),
                          playgroundHandlerFunction::mono
                         )
                ;
    }
}
