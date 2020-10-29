package review;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class BackPressure {


    @Test
    public void backPressureTest() {
        Flux<Integer> fluxBackPressure = Flux.range(1,10);

        StepVerifier
                .create(fluxBackPressure)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenRequest(1)
                .expectNext(3)
                .thenCancel()
                .verify();
    }

    @Test
    public void backPressureCustom1() {
        Flux<Integer> fluxBackPressure = Flux.range(1,10);

        fluxBackPressure
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        request(1);
                        System.out.println("Value requested: " + value);
                        if (value == 5) cancel();
                    }
                });
    }

    @Test
    public void backPressureCustom2() {
        Flux<String> fluxo =
                Flux.just("A","B","C","D")
                    .concatWith(Flux.just("E"));

        fluxo
                .subscribe(
                        (item) -> log.info("BackPressured_Letter is ONLY {}",item),
                        (e) -> System.out.println("the error: " + e),
                        () -> System.out.println("Terminou!!!"),
                        subscription -> subscription.request(2)
                          );
    }

}
