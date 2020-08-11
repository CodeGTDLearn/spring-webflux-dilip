package com.reactive.spring.playgroung.infinite_loop;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class InfiniteLoop {
    @Test
    void infiniteSequence() {
        Flux<Long> infiniteFlux = Flux
                .interval(Duration.ofMillis(200))
                .log();

        infiniteFlux
                .subscribe((element) -> System.out.println("Value is: " + element));

    }
}
