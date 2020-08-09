package com.reactive.spring.playgroung.flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {
    @Test
    void FluxText() {
        Flux<String> stringFlux =
                Flux.just("Spring","Spring boot","Reactive Spring")
                    //.concatWith(Flux.error(new RuntimeException("RunTime Exception Occured")))
                    .concatWith(Flux.just("After Error STEP"))
                    .log();

        stringFlux
                .subscribe(System.out::print,
                           (error) -> System.err.print("SOUT-ERROR: " + error),
                           () -> System.out.println("SOUT-COMMON: Completed - Subscription Done")
                          );
    }

    @Test
    void fluxText_WithoutError() {
        Flux<String> stringFlux =
                Flux.just("Spring","Spring boot","Reactive Spring")
                    .log();

        //TYPE 01
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .verifyComplete();

        //TYPE 02
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring","Spring boot","Reactive Spring")
                .verifyComplete();
    }


    @Test
    void fluxText_WithError() {
        Flux<String> stringFlux =
                Flux.just("Spring","Spring boot","Reactive Spring")
                    .concatWith(Flux.error(new RuntimeException("RunTime Exception Occured")))
                    .log();

        //TYPE 01
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();

        //TYPE 02
        StepVerifier
                .create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .expectErrorMessage("RunTime Exception Occured")
                .verify();
    }
}
