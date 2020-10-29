package review;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class FluxMonoFlatmapUsaEvents {

    //***************************************************//
    //**       'MAP' E 'FLATMAP'(TRANSFORMADORES)      **
    //***************************************************
    //**      FLATMAP EXECUTA METODOS QUE 'RETORNAM'   **
    //**     MONO OU FLUX, EX:  DAO-BD + REST-HTTP     **
    //***************************************************
    //**              FLATMAP SEQUENTIAL               **
    //**         RETORNA RESULTADO "ORDENADO"          **
    //**    E "MAIS RAPIDO", POR SER THREAD PARALLEL   **
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
    public void flatMap_SEM_ORDENACAO() {
        //FLATMAP DESTINADO A RESPOSTAS EXTERNAS(HTTP) + DB

        // 1 - FLUX_INICIAL (letras iniciais dos nomes)
        Flux<String> fluxDeIniciaisDeNomes = Flux.fromIterable(list);

        // 2 - Flatmap executa o "findbyInitial" "USANDO" CADA
        //     ELEMENTO do FLUX(FLUX_INICIAL )
        final Flux<String> FluxDeNomesRetornadosPeloFindbyInicial =
                fluxDeIniciaisDeNomes

                        //FORMATO 01: EXPRESSION LAMBDA
                        //.flatMap(this::findbyInitialName)

                        //FORMATO 02: STATEMENT LAMBDA
                        .flatMap((i) -> {
                            return findByInicial(i);
                        })
                        .log();

        StepVerifier
                .create(FluxDeNomesRetornadosPeloFindbyInicial)
                .expectSubscription()
                .expectNext("adam","antonio","Jenny","Janete")
                .verifyComplete();
    }

    @Test
    public void flatMap_FASTER_SEQUENTIAL() {

        Flux<String> primeiroFlux = Flux.fromIterable(list);

        final Flux<String> FluxAchatadoUnico =
                primeiroFlux
                        .flatMapSequential(this::findByInicial)
                        .log();

        StepVerifier
                .create(FluxAchatadoUnico)
                .expectSubscription()
                .expectNext("adam","antonio","Jenny","Janete")
                .verifyComplete();
    }

    private Flux<String> findByInicial(String initial) {
        List<String> listA = Arrays.asList("adam","antonio");
        List<String> listJ = Arrays.asList("Jenny","Janete");
        return initial.equals("A") ?
                Flux.fromIterable(listA) :
                Flux.fromIterable(listJ);
    }
}
