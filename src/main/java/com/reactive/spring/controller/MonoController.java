package com.reactive.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("dilipi")
public class MonoController {

    @GetMapping("mono")
    public Mono<Integer> UnBlockingMonoEndpoint(){
        return Mono
                .just(1)
                .log();
    }

}
