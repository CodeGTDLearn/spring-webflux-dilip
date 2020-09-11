package com.reactive.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
