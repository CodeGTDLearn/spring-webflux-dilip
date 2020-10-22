package playgroung.flux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxFactorys {

    List<String> listNames = Arrays.asList("adam","anna","jack","jenny");
    String[] arrayNames = new String[]{"adam","anna","jack","jenny"};

    @Test
    public void fluxWithIterable() {
        Flux<String> namesFlux = Flux
                .fromIterable(listNames)
                .log();

        StepVerifier
                .create(namesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();
    }

    @Test
    public void  fluxWithArray() {
        Flux<String> arrayNamesFlux = Flux
                .fromArray(arrayNames)
                .log();

        StepVerifier
                .create(arrayNamesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();
    }

    @Test
    public void  fluxWithStream() {
        Flux<String> streamNamesFlux = Flux
                .fromStream(listNames.stream())
                .log();

        StepVerifier
                .create(streamNamesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();
    }

    @Test
    public void  fluxUsingRange() {

        Flux<Integer> integerFlux = Flux
                .range(1,5)
                .log();

        StepVerifier
                .create(integerFlux)
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }
}
