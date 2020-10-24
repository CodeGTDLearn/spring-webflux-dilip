package review.flux;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Check {

    @Test
    public void fluxTest() {
        //A) Montagem de um Fluxo/Publisher de Strings:
        Flux<String> myFlow =

                // 1-Inseri alguns Strings no fluxo
                Flux.just("Paulo","Cintia","Ligia")

                    // 2-Concat. o 1º fluxo, c/ um 2º fluxo(sendo este ultimo
                    // carregado com erro)
                    //.concatWith(Flux.error(new RuntimeException("Excetion
                    // Occured!!!")))

                    // 3-Concat. o 1º fluxo, c/ um 2º fluxo(esse segundo carregado
                    // com outro string)
                    .concatWith(Flux.just("Completed - OK - Flux concatened"))
                    .log();


        //B) Usando o Fluxo Criado:
        myFlow

                // 1-Subscriber -> Subscribe no  Publisher
                // Publisher: Flux + Mono sao implentacoes de Publisher
                .subscribe(
                        // 2-Aplicando SOUT nos elements carregados no Flux
                        System.out::println,

                        // 3-Lambda detectora de erro
                        (error) -> System.err.println("This is the error: " + error),

                        // 4-Lambda executada ao final (Caso erro na interrompa o Flux)
                        () -> System.out.println("Finally Completed")
                          );

    }

    @Test
    public void fluxTest2() {

        Flux<String> flow = Flux
                .just("Primeiro","Segundo")
                //.concatWith(Flux.error(new RuntimeException("Break out")))
                .concatWith(Flux.just("Last one"))
                .log();

        flow.subscribe(
                System.out::println,
                (e) -> System.err.println("This is my error: " + e),
                () -> System.out.println("Passed without error!")
                      );

    }

    @Test
    public void fluxTest3() {
        Flux<String> fluxo =
                Flux.just("A","B")
                    //                    .concatWith(Flux.error(new RuntimeException("Error!")))
                    .concatWith(Flux.just("Finalized!"));

        fluxo
                .subscribe(
                        System.out::println,
                        (e) -> System.out.println("the error: " + e),
                        () -> System.out.println("Terminou!!!")
                          );
    }

    @Test
    public void fluxTest4() {
        Flux<String> fluxo = Flux
                .just("A","B")
                .concatWith(Flux.error(new RuntimeException("Error1")))
                .log();

        StepVerifier
                .create(fluxo)
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
                    .expectNext("A")
                    .expectErrorMessage("Error")
                    .verify();
    }
}
