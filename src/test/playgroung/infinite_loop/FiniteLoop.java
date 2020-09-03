package com.reactive.spring.playgroung.infinite_loop;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FiniteLoop {
    @Test
    void finiteSequenceUsingSleeping() throws InterruptedException {
        Flux<Long> finiteFlux = Flux
                .interval(Duration.ofMillis(100))
                .log();

        finiteFlux
                .subscribe((element) -> System.out.println("Value is: " + element));

        Thread.sleep(3000);
    }

    @Test
    void finiteSequenceUsingTake(){
        Flux<Long> finiteFlux = Flux
                .interval(Duration.ofMillis(100))
                .take(3)
                .log();

        StepVerifier
                .create(finiteFlux)
                .expectSubscription()
                .expectNext(0L,1L,2L)
                .verifyComplete();
    }

    @Test
    void finiteSequenceUsingDelay(){
        Flux<Integer> finiteFlux = Flux
                .interval(Duration.ofSeconds(1))
                .map(fluxElement -> new Integer(fluxElement.intValue()))
                .take(3)
                .log();

        StepVerifier
                .create(finiteFlux)
                .expectSubscription()
                .expectNext(0,1,2)
                .verifyComplete();
    }
}
