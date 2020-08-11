package com.reactive.spring.playgroung.backpressure;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class BackPressureTest {

    @Test
    void backPressureByRequestsByItself() {
        final Flux<Integer> finiteRange =
                Flux
                        .range(1,10)
                        .log();

        StepVerifier
                .create(finiteRange)
                .expectSubscription()
                .thenRequest(1)
                .thenRequest(2)
                .thenCancel()
                .verify();
    }

    @Test
    void backPressureByNumberOfRequests() {
        final Flux<Integer> finiteRange =
                Flux.range(1,10)
                    .log();

        finiteRange
                .subscribe((message) -> System.out.println("Message is: " + message),
                           (e) -> System.err.println("Exception E: " + e),
                           () -> System.out.println("Done!!!"),
                           (subscription -> subscription.request(2))
                          );
    }

    @Test
    void customBackPressure() {
        final Flux<Integer> finiteRange =
                Flux.range(1,10)
                    .log();

        finiteRange
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        request(1);
                        System.out.println("Value received from the flux: " +value);
                        if (value == 4) cancel();
                    }
                });
    }
}
