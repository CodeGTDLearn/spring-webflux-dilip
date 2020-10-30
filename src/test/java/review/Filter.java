package review;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class Filter {

    @Test
    public void filterFlux1() {

        // A) Cria umA SIMPLES lista de Strings
        List<String> list = Arrays.asList("adam","jenny");

        // B) Armazenar o Fluxo que sera trabalhado
        Flux<String> fs =

                // B) Carrega um Flux c/ a lista criada
                Flux.fromIterable(list)

                    // C) Filtra os items do Flux
                    .filter((item) -> item.startsWith("a"))

                    // D) Loga na tela os items filtrados
                    .log();

        StepVerifier
                .create(fs)
                .expectSubscription()
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void filterFlux2() {
        List<String> list = Arrays.asList("adam","jenny");

        Flux<String> fs = Flux
                .fromIterable(list)
                .filter(i -> i.startsWith("j"))
                .log();

        StepVerifier
                .create(fs)
                .expectSubscription()
                .expectNext("jenny")
                .verifyComplete();
    }

    @Test
    public void filterFlux3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(i -> i.startsWith("ad"))
                              .log();

        StepVerifier
                .create(fs)
                .expectSubscription()
                .expectNext("adam")
                .verifyComplete();
    }

    @Test
    public void filterFluxLenght1() {
        // A) Cria uma 'SIMPLES' lista de Strings
        List<String> list = Arrays.asList("adam","jenny");

        // B) Armazenar o Fluxo que sera trabalhado
        Flux<String> fs =

                // C) Carrega um Flux c/ a lista criada
                Flux.fromIterable(list)

                    // D) Filtra os items MAIORES QUE 4
                    .filter(i -> i.length() > 4)

                    // E) Loga na tela os items filtrados
                    .log();

        StepVerifier
                .create(fs)
                .expectSubscription()
                .expectNext("jenny");
    }

    @Test
    public void filterFluxLenght2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(e -> e.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectSubscription()
                .expectNext("jenny")
                .verifyComplete();
    }

    @Test
    public void filterFluxLenght3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(e -> e.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectSubscription()
                .expectNext("jenny")
                .verifyComplete();
    }
}
