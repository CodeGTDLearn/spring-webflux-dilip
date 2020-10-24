package review.mono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Load {

    @Test
    public void monoEmptyOrJust1() {
        Mono<String> mono = Mono.justOrEmpty(null);
        StepVerifier
                .create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoEmptyOrJust2() {
        Mono<String> mono = Mono.justOrEmpty(null);
        StepVerifier
                .create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoEmptyOrJust3() {
        Mono<String> mono = Mono.justOrEmpty(null);
        StepVerifier
                .create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier1() {
        Supplier<String> supplier = () -> "adam";
        Mono<String> mono = Mono.fromSupplier(supplier);
        StepVerifier
                .create(mono.log())
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier2() {
        Supplier<String> spl = () -> "adam";
        Mono<String> mn = Mono.fromSupplier(spl);
        StepVerifier
                .create(mn.log())
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier3() {
        Supplier<String> sp = () -> "adam";
        Mono<String> mn = Mono.fromSupplier(sp);
        StepVerifier
                .create(mn.log())
                .expectNext("adam")
                .verifyComplete();
    }


}
