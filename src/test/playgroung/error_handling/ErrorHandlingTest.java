package com.reactive.spring.playgroung.error_handling;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

public class ErrorHandlingTest {

    @Test
    public void exceptionExecutionOnErrorResume() {

        Flux<String> stringFlux = Flux
                .just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Runtime Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorResume((e) -> {
                    System.out.println("Exception OCCURED: " + e);
                    return Flux.just("ErrorFlux1","ErrorFlux2");
                });
        StepVerifier
                .create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                //.expectError(RuntimeException.class)
                //.verify();
                .expectNext("ErrorFlux1","ErrorFlux2")
                .verifyComplete();


    }

    @Test
    public void exceptionCustomErrorReturn() {

        Flux<String> stringFlux = Flux
                .just("A","B","C")
                .concatWith(Flux.error(new RuntimeException(" runtime Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("Custom Error Returned");

        StepVerifier
                .create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("Custom Error Returned")
                .verifyComplete();
    }

    @Test
    public void exceptionCustomException() {

        Flux<String> stringFlux = Flux
                .just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Runtime Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new);
        //                .onErrorMap((e)-> new CustomException(e));

        StepVerifier
                .create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    public void exceptionREtryExecution() {

        Flux<String> stringFlux = Flux
                .just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Runtime Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new)
                .retry(2);  // 02 ATTEMPTS


        StepVerifier
                .create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("A","B","C") // ATTEMPT 1
                .expectNext("A","B","C") // ATTEMPT 2
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    void exceptionREtryExecutionWhen() {
        Flux<String> stringFlux = Flux
                .just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new)
                .retryWhen(Retry.backoff(2,Duration.ofSeconds(5)));

        StepVerifier
                .create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("A","B","C")
                .expectNext("A","B","C")
                .expectError(IllegalStateException.class)
                .verify();
    }

}
