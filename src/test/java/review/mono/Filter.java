package review.mono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class Filter {

    @Test
    public void filter1() {

        // A) Cria uma 'SIMPLES' lista de Strings
        List<String> list = Arrays.asList("adam","jenny");

        // B) Armazenar o Fluxo que sera trabalhado
        Flux<String> fs =

                // C) Carrega um Flux c/ a lista criada
                Flux.fromIterable(list)

                    // D) Filtra os items do Flux
                    .filter((item) -> item.startsWith("a"))

                    // E) Loga na tela os items filtrados
                    .log();

        StepVerifier
                .create(fs)
                .expectNext("adam")
                .verifyComplete();


    }

    @Test
    public void filter2() {
        List<String> list = Arrays.asList("adam","jenny");

        Flux<String> fs = Flux
                .fromIterable(list)
                .filter(i -> i.startsWith("j"))
                .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();
    }

    @Test
    public void filter3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(i -> i.startsWith("ad"))
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("adam")
                .verifyComplete()
        ;
    }

    @Test
    public void filterLenght1() {
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
                .expectNext("jenny")
                .verifyComplete();

    }

    @Test
    public void filterLenght2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(e -> e.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();
    }

    @Test
    public void filterLenght3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fs = Flux.fromIterable(list)
                              .filter(e -> e.length() > 4)
                              .log();

        StepVerifier
                .create(fs)
                .expectNext("jenny")
                .verifyComplete();
    }
}
