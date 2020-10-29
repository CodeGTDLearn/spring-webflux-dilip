package review;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class LoadFlux {

    @Test
    public void fluxIterable1() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fList = Flux.fromIterable(list);
        StepVerifier.create(fList)
                    .expectSubscription()
                    .expectNext("adam","jenny")
                    .verifyComplete();
    }

    @Test
    public void fluxIterable2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> flist = Flux.fromIterable(list);
        StepVerifier
                .create(flist)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxIterable3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fList = Flux.fromIterable(list);
        StepVerifier
                .create(fList)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();

    }

    @Test
    public void fluxFromArray1() {
        String[] namesArray = new String[]{"adam","jenny"};
        Flux<String> namesFlux = Flux.fromArray(namesArray);

        StepVerifier
                .create(namesFlux)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromArray2() {
        String[] namesArray = new String[]{"adam","jenny"};
        Flux<String> fArray = Flux.fromArray(namesArray);

        StepVerifier
                .create(fArray)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromArray3() {
        String[] namesArray = new String[]{"adam","jenny"};
        Flux<String> fArray = Flux.fromArray(namesArray);

        StepVerifier
                .create(fArray)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream1() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> slist = Flux.fromStream(list.stream());
        StepVerifier
                .create(slist)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> flist = Flux.fromStream(list.stream());

//        flist.subscribe(item -> log.info("Name is {}",item));

        StepVerifier
                .create(flist)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fList = Flux.fromStream(list.stream());
        StepVerifier
                .create(fList)
                .expectSubscription()
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxRange1() {
        Flux<Integer> intFlux = Flux.range(1,3);
        StepVerifier
                .create(intFlux)
                .expectSubscription()
                .expectNext(1,2,3)
                .verifyComplete();
    }

    @Test
    public void fluxRange2() {
        Flux<Integer> df = Flux.range(1,3);
        StepVerifier
                .create(df)
                .expectSubscription()
                .expectNext(1,2,3)
                .verifyComplete();
    }

    @Test
    public void fluxRange3() {
        Flux<Integer> fi = Flux.range(1,2);
        StepVerifier
                .create(fi)
                .expectSubscription()
                .expectNext(1,2)
                .verifyComplete();
    }
}
