package review;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

@Slf4j
public class SchedulerIntervalVirtualtime {
    /*
        AGENDAMENTO INTERVALADO

        CONTEXTO:
              PUBLICA ALGUM ACAO/DADO DENTRO DE UM
              INTERVALO LIMITADO OU INDEFINIDO
        */
    @Test
    public void IntervalScheduler_TriggerAgendadoEmIntervalos() throws InterruptedException {
    /*
             1 - ESTE FLUX FOI AGENDADO P/ EMITIR A CADA 100ms
             OBSERVACAO:
                  DEVIDO AO AGENDAMENTO, BLOQUEARIA A THREAD 'MAIN'
                  PARA EVITAR O BLOQUEIO
                  O REACTOR JOGA OS FLUX-AGENDADOS P/ THREAD 'SECUNDARIA/PARALLEL'
    */
        Flux<Long> fluxoAgendadoIntervalado =
                Flux.interval(Duration.ofMillis(100))
                    .log();

        // 2 - EXECUTA O FLUXO-AGENDADO-INTERVALADO
        fluxoAgendadoIntervalado
                .subscribe(
                        (i) -> log.info("Numero: {}",i)
                          );

        // 3 - MOSTRA A THREAD PARALELA RODANDO 'POR 3000ms'
        Thread.sleep(3000);
    }

    @Test
    public void IntervalScheduler_limitadandoeXECUCOES() throws InterruptedException {
        // AGENDAMENTO INTERVALADO COM LIMITACAO DE QTDE DE EXECUCOES
        //
        // CONTEXTO:
        //      PUBLICA UM NUMERO LIMITADO DE EXECUCOES
        //      DENTRO DO INTERVALO LIMITADO OU INDEFINIDO

        // 1 - FLUX AGENDADO P/ EMITIR A CADA 100ms, LIMITADO A 5 EXECUCOES
        Flux<Long> fluxoAgendadoIntervalado =
                Flux.interval(Duration.ofMillis(100))
                    .take(5)
                    .log();

        // 2 - EXECUTA O FLUXO-AGENDADO
        fluxoAgendadoIntervalado
                .subscribe(
                        (i) -> log.info("Numero: {}",i)
                          );

        // 3 - CHECA A THREAD PARALELA RODANDO A CONTAGEM
        Thread.sleep(3000);
    }
    /*
        VIRTUAL-TIME TESTING:

            CONCEITO:
                        E um tipo de teste que virtualizao o clock do PC
                        Tornando o test MAIS RAPIDO

            APLICACAO:
                        'SOMENTE' aplica-se a casos c/ o USO-DE-DELAY
     */

    @Test
    public void IntervalScheduler_TesteSemVirtualTime() {
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                                  .take(3);

        StepVerifier
                .create(longFlux.log())
                .expectSubscription()
                .expectNext(0l,1l,2l)
                .verifyComplete();
    }

    @Test
    public void IntervalScheduler_TesteComVirtualTime() {

        VirtualTimeScheduler.getOrSet();

        Flux<Long> longFluxInterval = Flux.interval(Duration.ofSeconds(1))
                                          .take(3);

        StepVerifier.withVirtualTime(() -> longFluxInterval)
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(3))
                    .expectNext(0l,1l,2l)
                    .verifyComplete();
    }

    @Test
    public void IntervalScheduler_TesteRealComVirtualTime() {

        VirtualTimeScheduler.getOrSet();

        Flux<String> interval1 =
                Flux.just("A","B")
                    .delayElements(Duration.ofSeconds(1));

        Flux<String> interval2 =
                Flux.just("C","D")
                    .delayElements(Duration.ofSeconds(1));

        Flux<String> longFluxInterval = Flux.concat(interval1,interval2);

        StepVerifier.withVirtualTime(() -> longFluxInterval.log())
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(6)) // for virtualTime this is mandatory
                    .expectNextCount(4)
                    .verifyComplete();
    }

}
