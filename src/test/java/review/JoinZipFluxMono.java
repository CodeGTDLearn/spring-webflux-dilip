package review;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class JoinZipFluxMono {

    Flux<String> flux1 = Flux.just("A","a")
                             .delayElements(Duration.ofSeconds(1));
    Flux<String> flux2 = Flux.just("B","b")
                             .delayElements(Duration.ofSeconds(1));
    Flux<String> flux3 = Flux.just("C","c")
                             .delayElements(Duration.ofSeconds(1));

    @Test
    public void Merge_shuffleFluxes() {
        Flux<String> mergedFlux = Flux.merge(flux1,flux2,flux3);

        StepVerifier
                .create(mergedFlux.log())
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void Concat_FluxAfterFlux() {
        //CRIA UM NOVO FLUX COM A CONCATENACAO
        Flux<String> concatFlux = Flux.concat(flux1,flux2,flux3);

        StepVerifier
                .create(concatFlux.log())
                .expectSubscription()
                .expectNext("A","a","B","b","C","c")
                .verifyComplete();
    }

    @Test
    public void Concat_With() {
        //CONCATENA, NUM MESMO FLUX
        Flux<String> fl = Flux
                .just("A")
                .concatWith(Flux.just("B"))
                .log();

        StepVerifier.create(fl)
                    .expectSubscription()
                    .expectNext("A","B")
                    .verifyComplete();
    }

    @Test
    public void Zip_FundeOrdenadamenteElementosDosFluxos() {
        Flux<String> concatFlux =
                Flux
                        .zip(flux1,flux2,(t1,t2) -> {
                            return t1.concat(t2);
                        });

        StepVerifier
                .create(concatFlux.log())
                .expectSubscription()
                .expectNext("AB","ab")
                .verifyComplete();
    }
}
