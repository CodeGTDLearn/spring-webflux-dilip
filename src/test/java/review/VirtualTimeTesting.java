package review;

import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTesting {

    /*
        VIRTUAL-TIME TESTING:

            CONCEITO:
                        E um tipo de teste que virtualizao o clock do PC
                        Tornando o test MAIS RAPIDO

            APLICACAO:
                        'SOMENTE' aplica-se a casos c/ o USO-DE-DELAY
     */
    @Test
    public void TesteSemVirtualTime() {
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                                  .take(3);

        StepVerifier
                .create(longFlux.log())
                .expectSubscription()
                .expectNext(0l,1l,2l)
                .verifyComplete();
    }

    @Test
    public void TesteComVirtualTime() {

        VirtualTimeScheduler.getOrSet();

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                                  .take(3);

        StepVerifier.withVirtualTime(() -> longFlux)
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(3))
                    .expectNext(0l,1l,2l)
                    .verifyComplete();
    }

    @Test
    public void TesteRealComVirtualTime() {

        VirtualTimeScheduler.getOrSet();

        Flux<String> flux1 =
                Flux.just("A","B")
                    .delayElements(Duration.ofSeconds(1));

        Flux<String> flux2 =
                Flux.just("C","D")
                    .delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.concat(flux1,flux2);

        StepVerifier.withVirtualTime(() -> mergedFlux.log())
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(6)) // for virtualTime this is mandatory
                    .expectNextCount(4)
                    .verifyComplete();
    }
}
