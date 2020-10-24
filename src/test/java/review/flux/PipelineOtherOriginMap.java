package review.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class PipelineOtherOriginMap {

    List<String> list = Arrays.asList("adam","jenny");

    @Test
    public void mapTransform() {
        //1 - Variavel FLOW armazenara a pipeline
        Flux<String> flow =

                //2 - Cria um FLUXO
                Flux.fromIterable(list)

                    //3 - Cria a pipeline: Tranformando o FLUXO criado
                    .map(item -> item.toUpperCase())
                    .log();

        StepVerifier
                .create(flow)
                .expectNext("ADAM","JENNY")
                .verifyComplete();
    }

    @Test
    public void mapLenght1() {

        Flux<Integer> flowStorage =
                Flux.fromStream(list.stream())
                    .map(item -> item.length())
                    .log();

        StepVerifier
                .create(flowStorage)
                .expectNext(4,5)
                .expectComplete();
    }

    @Test
    public void mapLenght2() {
        Flux<String> pipeline =
                Flux.fromStream(list.stream())
                    .filter(item -> item.length() > 4)
                    .map(String::toUpperCase)
                    .log();

        StepVerifier
                .create(pipeline)
                .expectNext("JENNY")
                .expectComplete();
    }

    @Test
    public void mapRepeat() {

        Flux<Integer> pipeline =
                Flux.fromIterable(list)
                    .map(String::length)
                    .repeat(1)
                    .log();

        StepVerifier
                .create(pipeline)
                .expectNext(4,5,4,5)
                .verifyComplete();


    }

    @Test
    public void transformFilter2() {

        Flux<String> pipeline =
                Flux.fromIterable(list)
                .filter(i -> i.length() == 4)
                .map(x -> x.toUpperCase())
                .log();

        StepVerifier
                .create(pipeline)
                .expectNext("ADAM")
                .verifyComplete();

    }
}
