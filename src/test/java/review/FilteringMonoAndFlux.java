package review;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FilteringMonoAndFlux {

    @Test
    public void filter1() {
        List<String> list = Arrays.asList("adam","jenny");

        Flux<String> fs =
                Flux.fromIterable(list)
                    .filter((item) -> item.startsWith("a"))
                    .log();

        StepVerifier
                .create(fs)
                .expectNext("adam")
                .verifyComplete();


    }

    @Test
    public void filter2() {
        List<String> list = Arrays.asList("adam","jenny");

        Flux<String> fs = Flux
                .fromIterable(list)
                .filter(i -> i.startsWith("j"))
                .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();
    }

    @Test
    public void filter3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(i -> i.startsWith("ad"))
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("adam")
                .verifyComplete()
        ;
    }

    @Test
    public void filterLenght1() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(i -> i.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();

    }

    @Test
    public void filterLenght2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(e -> e.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();
    }

    @Test
    public void filterLenght3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(e -> e.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();
    }
}
