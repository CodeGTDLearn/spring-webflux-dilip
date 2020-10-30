package review;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ExceptionHandler {


    Flux<String> FluxoGeralParaTest = Flux.just("A","B","C");

    @Test
    public void ExcecaoSimples() {
        Flux<String> result =
                FluxoGeralParaTest
                        .concatWith(Flux.error(new RuntimeException("Excecao aconteceu")))
                        .concatWith(Flux.just("Dfinal"));

        StepVerifier
                .create(result.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fluxError2() {
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
    public void CustomExcecaoFlux() {
        Flux<String> result =
                FluxoGeralParaTest
                        .concatWith(Flux.error(new RuntimeException("Excecao aconteceu")))
                        .concatWith(Flux.just("Dfinal"))
                        .onErrorMap((erro) -> {
                            return new CustomExcept(erro);
                        });

        StepVerifier
                .create(result.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectError(CustomExcept.class)
                .verify();
    }

    @Getter
    @Setter
    class CustomExcept extends Throwable {
        private String message;

        public CustomExcept(Throwable error) {
            this.message = error.getMessage();
        }
    }
}

