package review;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class PublisherColdReactiveStream {

    /*

        COLD REACTIVE STREAM OR COLD PUBLISHER

        CONCEITO:
                - DOIS SUBSCRIBERS EMITEM ELEMENTO DE UMA 'STREAM/FLUX'
                - O SEGUNDO, EMITE OS ELEMENTOS DESDE O INICIO(ELEMENTO INICIAL)
     */
    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> strFlux =
                Flux
                        .just("A","B","C","D","E","F")
                        .delayElements(Duration.ofSeconds(1));

        strFlux.subscribe((i) -> System.out.println("Subscriber 01: " + i));
        Thread.sleep(2000);

        strFlux.subscribe((i) -> System.out.println("Subscriber 02: " + i));
        Thread.sleep(4000);


    }
}
