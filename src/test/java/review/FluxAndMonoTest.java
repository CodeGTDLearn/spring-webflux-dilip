package review;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

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
}
