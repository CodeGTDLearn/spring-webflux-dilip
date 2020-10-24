package review.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PipelineDbHttpFlatmap {

    //***************************************************//
    //**       DIFERENCA ENTRE 'MAP' E 'FLATMAP'       **
    //***************************************************
    //**      FLATMAP EXECUTA METODOS QUE 'RETORNAM'   **
    //**     MONO OU FLUX, EX:  DAO-BD + REST-HTTP     **
    //***************************************************
    //**              FLATMAP SEQUENTIAL               **
    //**         RETORNA RESULTADO ORDENADO            **
    //**    E MAIS RAPIDO, POR SER THREAD PARALLEL     **
    //***************************************************
    //**       MAP EXECUTA METODOS QUE 'NAO RETORNAM'  **
    //**                  MONO OU FLUX                 **
    //***************************************************//
    //  EXPLICACAO:
    //  MAP: EXECUTA 'METODOS QUE NAO RETORNAM FLUX/MONO'
    //  CASO CONTRARIO:
    //     EX: UM 'MAP' QUE EXECUTE UM 'METODO C/ RETORNO FLUX<String>(por exemplo)'
    //        RESULTARA EM 'publisher dobrado' --> 'Flux<Flux<String>>'
    //           pois o MAP executou um 'Metodo c/ retorno 'Flux<String>'',
    //           baseando-se num 'elemento advindo de outro 'Flux<String>''
    //
    //     EX. NO CODIGO
    //  Flux<Flux<String>> fluxDobradoDuplo =
    //         primeiroFlux
    //                    .map(this::findbyNameRetornaSegundoFlux)
    //                    .log();

    // FLATMAP: EXECUTA 'METODOS QUE RETORNAM FLUX/MONO'
    //   RESULTANDO num FLUX-FLATED(ACHATADO), ou seja 'Flux<String>'


    List<String> list = Arrays.asList("A","B");

    Flux<String> fList = Flux.fromIterable(list);

    @Test
    public void flatMapd_SEM_ORDENACAO() {

        //FLATMAP DESTINADO A RESPOSTAS EXTERNAS + DB
        Flux<String> primeiroFlux = Flux.fromIterable(list);


        final Flux<String> FluxAchatadoUnico =
                primeiroFlux
                        .flatMap(this::findbyNameRetornaSegundoFlux)
                        .log();

        StepVerifier
                .create(FluxAchatadoUnico)
                .expectSubscription()
                .expectNext("adam","antonio","Jenny","Janete")
                .verifyComplete();
    }

    @Test
    public void flatMap_FASTER_COM_ORDENACAO() {

        Flux<String> primeiroFlux = Flux.fromIterable(list);

        final Flux<String> FluxAchatadoUnico =
                primeiroFlux
                        .flatMapSequential(this::findbyNameRetornaSegundoFlux)
                        .log();

        StepVerifier
                .create(FluxAchatadoUnico)
                .expectSubscription()
                .expectNext("adam","antonio","Jenny","Janete")
                .verifyComplete();
    }

    public Flux<String> findbyNameRetornaSegundoFlux(String initial) {
        List<String> listA = Arrays.asList("adam","antonio");
        List<String> listJ = Arrays.asList("Jenny","Janete");
        return initial.equals("A") ?
                Flux.fromIterable(listA) :
                Flux.fromIterable(listJ);
    }
}
