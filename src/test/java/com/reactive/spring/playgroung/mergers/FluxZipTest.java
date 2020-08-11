package com.reactive.spring.playgroung.mergers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxZipTest {
    @Test
    public void fluxZip() {

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> zipingFlux = Flux
                .zip(flux1,flux2,
                         (t1,t2) -> {
                             return t1.concat(t2);
                         }
                    );

        StepVerifier
                .create(zipingFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }
}
