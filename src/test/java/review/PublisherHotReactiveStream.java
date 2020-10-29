package review;

import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class PublisherHotReactiveStream {

    /*

        HOT REACTIVE STREAM OR HOT PUBLISHER

        CONCEITO:
                - DOIS SUBSCRIBERS EMITEM ELEMENTOS DE UMA 'STREAM/FLUX'
                - O SEGUNDO, EMITE SEUS ELEMENTOS EM SEQUENCIA(ENCADEADOS)
                  C/ OS ELEMENTOS PRIMEIRA
     */
    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> strFlux =
                Flux.just("A","B","C","D","E","F")
                    .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> conFlux = strFlux.publish();
        conFlux.connect();

        conFlux.subscribe(i -> System.out.println("Subscriber 01: " + i));
        Thread.sleep(3000);

        conFlux.subscribe((i) -> System.out.println("Subscriber 02: " + i));
        Thread.sleep(4000);


    }
}
