package com.reactive.spring.playgroung.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {
    @Test
    void monoTestWithoutError() {

        Mono<String> stringMono = Mono.just("Spring");

        StepVerifier
                .create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    void monoTestWithError() {

        StepVerifier
                .create(Mono.error(new RuntimeException("Exception Occured")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
