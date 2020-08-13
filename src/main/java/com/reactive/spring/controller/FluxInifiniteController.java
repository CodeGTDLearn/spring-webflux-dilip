package com.reactive.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("dilipi")
public class FluxInifiniteController {

    @GetMapping(value = "flux-stream-infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> UNBlockingInfiniteEndPoint(){
        return Flux
                .interval(Duration.ofSeconds(1))
                .log();
    }

}
