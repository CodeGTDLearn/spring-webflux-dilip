package com.reactive.spring.router;

import com.reactive.spring.handler.CRUD.ItemsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.reactive.spring.config.MappingsHandler.*;
import static com.reactive.spring.config.MappingsStream.STREAM_ENDPOINT_FUNC;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class ItemsRouter {

    final MediaType JSON = MediaType.APPLICATION_JSON;

    @Bean
    public RouterFunction<ServerResponse> itemsRoute(ItemsHandler handler) {
        return RouterFunctions
                .route(GET(VERS_FUNCT_ENDPT).and(accept(JSON)),handler::getAll)
                .andRoute(POST(VERS_FUNCT_ENDPT).and(accept(JSON)),handler::save)
                .andRoute(GET(VERS_FUNCT_ENDPT_ID).and(accept(JSON)),handler::getById)
                .andRoute(DELETE(VERS_FUNCT_ENDPT_ID).and(accept(JSON)),handler::delete)
                .andRoute(PUT(VERS_FUNCT_ENDPT_ID).and(accept(JSON)),handler::update)
                ;
    }

    @Bean
    public RouterFunction<ServerResponse> errorRoute(ItemsHandler handler) {
        return RouterFunctions
                .route(GET(VERS_FUNCT_ENDPT_EXCEPT).and(accept(JSON)),handler::except);
    }

    @Bean
    public RouterFunction<ServerResponse> itemStreamRoute(ItemsHandler handler) {
        return RouterFunctions
                .route(GET(STREAM_ENDPOINT_FUNC).and(accept(JSON)),handler::itemStream);
    }

}
