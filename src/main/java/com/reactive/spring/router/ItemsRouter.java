package com.reactive.spring.router;

import com.reactive.spring.handler.ItemsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.reactive.spring.config.MappingsHandler.VERS_FUNCT_ENDPT;
import static com.reactive.spring.config.MappingsHandler.VERS_FUNCT_ENDPT_ID;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class ItemsRouter {

    final MediaType JSON = MediaType.APPLICATION_JSON;

    @Bean
    public RouterFunction<ServerResponse> itemsRoute(ItemsHandler itemsHandler) {
        return RouterFunctions
                .route(GET(VERS_FUNCT_ENDPT).and(accept(JSON)),itemsHandler::getAll)
                .andRoute(POST(VERS_FUNCT_ENDPT).and(accept(JSON)),itemsHandler::save)
                .andRoute(GET(VERS_FUNCT_ENDPT_ID).and(accept(JSON)),itemsHandler::getById)
                .andRoute(DELETE(VERS_FUNCT_ENDPT_ID).and(accept(JSON)),itemsHandler::delete)
                .andRoute(PUT(VERS_FUNCT_ENDPT_ID).and(accept(JSON)),itemsHandler::update)
                ;
    }

}
