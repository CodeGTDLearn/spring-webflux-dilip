package com.reactive.spring.playgroung.flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxFilterTest {

    List<String> listNames = Arrays.asList("adam","anna","jack","jenny");

    @Test
    public void fluxWithIterable() {
        Flux<String> namesFlux = Flux
                .fromIterable(listNames)
                .filter(initialLetter ->initialLetter.startsWith("a"))
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext("adam","anna")
                .verifyComplete();
    }


    @Test
    public void fluxTestLenght() {
        Flux<String> namesFlux = Flux
                .fromIterable(listNames)
                .filter(wordLenght ->wordLenght.length() > 4)
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext("jenny")
                .verifyComplete();
    }
}
