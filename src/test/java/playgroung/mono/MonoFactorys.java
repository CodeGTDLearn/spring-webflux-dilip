package playgroung.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


public class MonoFactorys {

    @Test
    public void  monoUsingJustOrEmpty() {
        Mono<String> mono = Mono
                .justOrEmpty(null);

        StepVerifier
                .create(mono.log())
                .verifyComplete();
    }

    @Test
    public void  monoUsingSupplier() {

        Supplier<String> stringSupplier = () -> "adam";

        Mono<String> stringMono = Mono
                .fromSupplier(stringSupplier);

        StepVerifier
                .create(stringMono)
                .expectNext("adam")
                .verifyComplete();
    }
}
