package review;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class BackPressure {

    @Test
    public void backPressureEmTestes() {
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
    public void backPressureTriggerAction() {
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

    // LIMITA O NUMERO DE EVENTOS NO CHANNEL
    // NAO RODA T_ODOS EVENTOS DO FLUX: [NAO ALCANCANDO O ONCOMPLETE]
    @Test
    public void backPressureSimplesLimitador() {
        Flux<String> fluxo =
                Flux.just("A","B","C","D","F","G","H")
                    .concatWith(Flux.just("E")).log();

        fluxo.subscribe(
                (item) -> log.info("BackPressured_Letter is ONLY {}",item),
                (e) -> System.out.println("the error: " + e),
                () -> System.out.println("Terminou!!!"),
                subscription -> subscription.request(3)
                       );
    }

    // 'REPETE REQUEST POR PASSO' P/ OS EVENTOS NO CHANNEL
    // RODA T_ODOS EVENTOS DO FLUX [ALCANCANDO O ONCOMPLETE]
    @Test
    public void backPressurePorPassoBaseSubscriber() {
        Flux<Integer> fluxo = Flux.range(1,10)
                                  .log();

        fluxo.subscribe(new BaseSubscriber<Integer>() {
            private int counter = 0;
            private final int requestCount = 3;

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(requestCount);
            }

            @Override
            protected void hookOnNext(Integer value) {
                counter++;
                if (counter >= requestCount) {
                    counter = 0;
                    request(requestCount);
                }
            }
        });
    }

    // 'REPETE REQUEST POR PASSO' P/ OS EVENTOS NO CHANNEL
    // RODA T_ODOS EVENTOS DO FLUX [ALCANCANDO O ONCOMPLETE]
    @Test
    public void backPressurePorPassoSLIM() {
        Flux<Integer> fluxo =
                Flux.range(1,10)
                    .log()
                    .limitRate(3);

        log.info("-------------------------");

        StepVerifier
                .create(fluxo)
                .expectSubscription()
                .expectNext(1,2,3,4,5,6,7,8,9,10)
                .verifyComplete();
    }

}
