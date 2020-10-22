package review;

import lombok.var;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CheckingMonoTest {

    @Test
    public void monoTest1() {
        Mono<String> monoFlow =
                Mono.just("A")
                    .log();

        StepVerifier.create(monoFlow)
                    .expectNext("A")
                    .verifyComplete();
    }

    @Test
    public void monoTest2() {

        StepVerifier
                .create(Mono.error(new RuntimeException("ErrorMono")).log())
                .expectErrorMessage("ErrorMono")
                .verify();

    }
}
