package com.reactive.spring.playgroung.transformers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class MapTransformerTest {

    List<String> listNames = Arrays.asList("adam","anna","jack","jenny");

    @Test
    public void mapChangesEachFluxElement() {
        Flux<String> namesFlux = Flux
                .fromIterable(listNames)
                .map(word -> word.toUpperCase())
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext("ADAM","ANNA","JACK","JENNY")
                .verifyComplete();
    }

    @Test
    public void mapChangesEachFluxElementUsingLenght() {
        Flux<Integer> namesFlux = Flux
                .fromIterable(listNames)
                .map(fluxElement -> fluxElement.length())
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext(4,4,4,5)
                .verifyComplete();
    }

    @Test
    public void mapChangesEachFluxElementUsingRepeat() {
        Flux<Integer> namesFlux = Flux
                .fromIterable(listNames)
                .repeat(1)
                .map(fluxElement -> fluxElement.length())
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext(4,4,4,5,4,4,4,5)
                .verifyComplete();
    }

    @Test
    public void mapChangesEachFluxElementUsingFilter() {
        Flux<String> namesFlux = Flux
                .fromIterable(listNames)
                .map(String::toUpperCase) // EXECUTES "toUpperCase" IN EACH STRING inside the FLUX
                .filter(word -> word.length() > 4)// EXECUTES "len" IN EACH STRING inside the FLUX
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext("JENNY")
                .verifyComplete();
    }
}
