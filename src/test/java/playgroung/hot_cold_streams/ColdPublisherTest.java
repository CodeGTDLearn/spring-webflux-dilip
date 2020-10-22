package playgroung.hot_cold_streams;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdPublisherTest {

    @Test
    void coldPublisherTest() throws InterruptedException {

        Flux<String> stringFlux = Flux
                .just("A","B","C")
                .delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe(message -> System.out.println("Subscriber 0111: " + message));
        Thread.sleep(2000);

        stringFlux.subscribe(message -> System.out.println("Subscriber 0222: " + message));
        Thread.sleep(4000);
    }

    @Test
    void hostPublisherTest() throws InterruptedException {

        Flux<String> stringFlux = Flux
                .just("A","B","C","D")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();

        connectableFlux.subscribe(message -> System.out.println("Subscriber 1:" + message));
        Thread.sleep(3000);

        connectableFlux.subscribe(message -> System.out.println("Subscriber 2:" + message));
        Thread.sleep(4000);


    }
}
