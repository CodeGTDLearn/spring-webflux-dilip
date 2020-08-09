package com.reactive.spring.playgroung.mergers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxMergersTest {

    @Test
    public void fluxMerger() {

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier
                .create(mergedFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    public void fluxMergerWithDelayNoOrder() {

        Flux<String> flux1 = Flux.just("A","B","C")
                                 .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F")
                                 .delayElements(Duration.ofSeconds(1));

        Flux<String> mergingFlux = Flux.merge(flux1,flux2);

        StepVerifier
                .create(mergingFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void fluxConcatNoDelayOrdered() {

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> concatingFlux = Flux.concat(flux1,flux2);

        StepVerifier
                .create(concatingFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void fluxConcatWithDelayOrdered() {

        Flux<String> flux1 = Flux.just("A","B","C")
                                 .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F")
                                 .delayElements(Duration.ofSeconds(1));

        Flux<String> concatingFlux = Flux.concat(flux1,flux2);

        StepVerifier
                .create(concatingFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

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
