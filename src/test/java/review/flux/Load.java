package review.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Load {

    @Test
    public void fluxIterable1() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fList = Flux.fromIterable(list);
        StepVerifier.create(fList)
                    .expectNext("adam","jenny")
                    .verifyComplete();
    }

    @Test
    public void fluxIterable2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> flist = Flux.fromIterable(list);
        StepVerifier
                .create(flist)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxIterable3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fList = Flux.fromIterable(list);
        StepVerifier
                .create(fList)
                .expectNext("adam","jenny")
                .verifyComplete();

    }

    @Test
    public void fluxFromArray1() {
        String[] namesArray = new String[]{"adam","jenny"};
        Flux<String> namesFlux = Flux.fromArray(namesArray);

        StepVerifier
                .create(namesFlux)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromArray2() {
        String[] namesArray = new String[]{"adam","jenny"};
        Flux<String> fArray = Flux.fromArray(namesArray);

        StepVerifier
                .create(fArray)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromArray3() {
        String[] namesArray = new String[]{"adam","jenny"};
        Flux<String> fArray = Flux.fromArray(namesArray);

        StepVerifier
                .create(fArray)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream1() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> slist = Flux.fromStream(list.stream());
        StepVerifier
                .create(slist)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream2() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> flist = Flux.fromStream(list.stream());
        StepVerifier
                .create(flist)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream3() {
        List<String> list = Arrays.asList("adam","jenny");
        Flux<String> fList = Flux.fromStream(list.stream());
        StepVerifier
                .create(fList)
                .expectNext("adam","jenny")
                .verifyComplete();
    }

    @Test
    public void fluxRange1() {
        Flux<Integer> intFlux = Flux.range(1,3);
        StepVerifier
                .create(intFlux)
                .expectNext(1,2,3)
                .verifyComplete();
    }

    @Test
    public void fluxRange2() {
        Flux<Integer> df = Flux.range(1,3);
        StepVerifier
                .create(df)
                .expectNext(1,2,3)
                .verifyComplete();
    }

    @Test
    public void fluxRange3() {
        Flux<Integer> fi = Flux.range(1,2);
        StepVerifier
                .create(fi)
                .expectNext(1,2)
                .verifyComplete();
    }
}
