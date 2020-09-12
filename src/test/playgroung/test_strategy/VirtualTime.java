package com.reactive.spring.test_strategy;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTime {

    @Test
    void noVirtualTime() {

        Flux<Long> flux = Flux
                .interval(Duration.ofSeconds(1))
                .take(4)
                .log();

        StepVerifier
                .create(flux.log())
                .expectSubscription()
                .expectNext(0L,1L,2L,3L)
                .verifyComplete();

    }

    @Test
    void VirtualTime() {

        VirtualTimeScheduler.getOrSet();

        Flux<Long> flux = Flux
                .interval(Duration.ofSeconds(1))
                .take(4)
                .log();

        StepVerifier
                .withVirtualTime(flux::log)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(4))
                .expectNext(0L,1L,2L,3L)
                .verifyComplete();

    }

    @Test
    void VirtualTimeConcat() {

        VirtualTimeScheduler.getOrSet();

        Flux<String> flux1 = Flux
                .just("A","B","C")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> flux2 = Flux
                .just("d","e","f")
                .delayElements(Duration.ofSeconds(1));

        final Flux<String> flux = Flux.concat(flux1,flux2);

        StepVerifier
                .withVirtualTime(flux::log)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6))
                .expectNextCount(6)
                .verifyComplete();

    }
}
