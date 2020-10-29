package review;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ErrorFlux {

    Flux<String> FluxoGeralParaTest = Flux.just("A","B","C");

    @Test
    public void fluxError1() {
        Flux<String> fluxo = Flux
                .just("A","B")
                .concatWith(Flux.error(new RuntimeException("Error1")))
                .log();

        StepVerifier
                .create(fluxo)
                .expectSubscription()
                .expectNext("A","B")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fluxTest5() {
        Flux<String> flux = Flux
                .just("A","B")
                .concatWith(Flux.error(new RuntimeException("Error:")))
                .log();

        StepVerifier
                .create(flux)
                .expectSubscription()
                .expectNext("A","B")
                .expectErrorMessage("Error:")
                .verify();
    }

    @Test
    public void fluxTest6() {
        Flux<String> fl = Flux
                .just("A")
                .concatWith(Flux.error(new RuntimeException("Error")))
                .log();

        StepVerifier.create(fl)
                    .expectSubscription()
                    .expectNext("A")
                    .expectErrorMessage("Error")
                    .verify();
    }

    @Test
    public void ErrorComResumo() {
        Flux<String> result =
                FluxoGeralParaTest
                        .concatWith(Flux.error(new RuntimeException("Excecao aconteceu")))
                        .concatWith(Flux.just("Dfinal"))
                        .onErrorResume((e) -> {
                            System.out.println("The Exception is:" + e);
                            return Flux.just("Resumo do Error");
                        });

        StepVerifier
                .create(result.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("Resumo do Error")
                .verifyComplete();
    }

    @Test
    public void ErrorComRetorno() {
        Flux<String> result =
                FluxoGeralParaTest
                        .concatWith(Flux.error(new RuntimeException("Excecao aconteceu")))
                        .concatWith(Flux.just("Dfinal"))
                        .onErrorReturn("Retorno Em caso de erro");

        StepVerifier
                .create(result.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("Retorno Em caso de erro")
                .verifyComplete();
    }
}
