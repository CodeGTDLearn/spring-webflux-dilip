package playgroung.mergers;

import lombok.var;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxConcatTest {
    @Test
    public void fluxConcatNoDelayOrdered() {

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> concatingFlux = Flux.concat(flux1,flux2);

        StepVerifier
                .create(concatingFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void fluxConcatWithDelayOrdered() {

        var flux1 = Flux.just("A","B","C")
                        .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F")
                                 .delayElements(Duration.ofSeconds(1));

        Flux<String> concatingFlux = Flux.concat(flux1,flux2);

        StepVerifier
                .create(concatingFlux.log())
                //CONFIRMS THE SUCESSFULL 'SUBSCRIPTION', AFTER SUBSCRIBE, BEFORE onNext
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }
}
