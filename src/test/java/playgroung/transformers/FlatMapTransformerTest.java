package playgroung.transformers;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FlatMapTransformerTest {

    List<String> listLetters = Arrays.asList("A","B","C");

    @Test
    public void flatmapTransformsFluxElementsApplingMethodsOnThose() {
        Flux<String> stringFlux = Flux
                .fromIterable(listLetters)
                .flatMap(fluxLetter -> {
                    return Flux.fromIterable(createListUsingLetter(fluxLetter));
                })
                .log();

        StepVerifier
                .create(stringFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void flatmapTransformsFluxElementsApplingMethodsOnThoseWithParallel() {
        Flux<String> stringFlux = Flux
                .fromIterable(listLetters)
                .window(2)
                .flatMap(fluxLetter -> fluxLetter
                        .map(this::createListUsingLetter)
                        .subscribeOn(parallel())
                        .flatMap(s -> Flux.fromIterable(s)))
                .log();

        StepVerifier
                .create(stringFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void flatmapTransformsFluxElementsApplingMethodsOnThoseWithParallelInOrder() {
        Flux<String> stringFlux = Flux
                .fromIterable(listLetters)
                .window(2)
                .flatMapSequential(fluxLetter -> fluxLetter
                        .map(this::createListUsingLetter)
                        .subscribeOn(parallel())
                        .flatMap(s -> Flux.fromIterable(s)))
                .log();

        StepVerifier
                .create(stringFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    private List<String> createListUsingLetter(String fluxLetter) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(fluxLetter,"newValue");
    }

}
