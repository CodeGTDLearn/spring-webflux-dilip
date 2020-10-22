package review;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CheckingFluxTest {

    @Test
    public void fluxTest() {
        Flux<String> myFlow = Flux
                .just("Paulo","Cintia","Ligia")
                //                .concatWith(Flux.error(new RuntimeException("Excetion
                //                Occured!!!")))
                .concatWith(Flux.just("Completed - OK - Flux concatened"))
                .log();

        myFlow
                .subscribe(System.out::println,
                           (error) -> System.err.println("This is the error: " + error),
                           () -> System.out.println("Finally Completed")
                          );

    }

    @Test
    public void fluxTest2() {

        Flux<String> flow = Flux
                .just("Primeiro","Segundo")
                //                .concatWith(Flux.error(new RuntimeException("Break out")))
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
