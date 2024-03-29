package review;

import lombok.var;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ReadMono {

    @Test
    public void monoTest1() {
        //A) Montagem de um Mono/Publisher de Strings:
        Mono<String> monoFlow =

                // 1- Inseri um String no fluxo
                Mono.just("A")

                    // 2- Loga na tela os items filtrados
                    .log();

        StepVerifier.create(monoFlow)
                    .expectSubscription()
                    .expectNext("A")
                    .verifyComplete();
    }

    @Test
    public void monoTest2() {

        StepVerifier
                .create(Mono.error(new RuntimeException("ErrorMono")).log())
                .expectSubscription()
                .expectErrorMessage("ErrorMono")
                .verify();

    }
}
