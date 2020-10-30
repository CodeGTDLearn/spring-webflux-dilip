package review;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class FlatmapMapAchatado {

    //***************************************************//
    //**       'MAP' E 'FLATMAP'(TRANSFORMADORES)      **
    //***************************************************
    //**               FLATMAP 'EXECUTA'               **
    //**      "METODOS QUE RETORNAM MONO OU FLUX"      **
    //**          EX:   DAO-BD + REST-HTTP             **
    //**     MONO OU FLUX, EX:  DAO-BD + REST-HTTP     **
    //***************************************************
    //**              FLATMAP SEQUENTIAL               **
    //**         RETORNA RESULTADO "ORDENADO"          **
    //**    E "MAIS RAPIDO", POR SER THREAD PARALLEL   **
    //***************************************************
    //**                MAP 'EXECUTA'                  **
    //**    METODOS QUE 'NAO RETORNAM MONO OU FLUX     **
    //***************************************************//

    // MAP: EXECUTA "METODOS QUE NAO RETORNAM FLUX/MONO"
    // EXPLICACAO:
    // A) 'MAP' RETORNA UM FLUX
    // B) 'MAP' QUE EXECUTE, UM METODO QUE "TB" RETORNE UM FLUX(ex: Flux<String>)
    //    --> RESULTARA EM 'publisher dobrado'[FluxArray] 'Flux<Flux<String>>'
    //        Pois:
    //           * o MAP (que retorna FLUX);
    //           * executou um 'Metodo q/ "TAMBEM" FLUX (ex: 'Flux<String>'),
    //               # Resultando em:   'Flux<Flux<String>>'
    //
    //     --> EX. NO CODIGO
    //  Flux<Flux<String>> fluxDobradoDuplo =
    //         primeiroFluxRetornadoPeloMAP
    //                    .map(this::findbyNameSegundoFluxRetornaDesteMetpdp)
    //                    .log();

    // FLATMAP: EXECUTA 'METODOS QUE "RETORNAM" FLUX/MONO'
    // EXPLICACAO:
    // A) 'FLATMAP' RETORNA UM "MAP ACHATADO"(FLATTED-MAP)
    // B) 'FLATMAP' QUE EXECUTE, UM METODO QUE RETORNE UM FLUX(ex: Flux<String>)
    //    --> RESULTARA NO, FLUX DO METODO EXECUTADO (ex: Flux<String>)
    // C) RESUMINDO:
    //    --> 'FLATMAP' E UM 'MAP' QUE EVITA 'publisher dobrado'[FluxArray]
    //        --> PORTANTO, RETORNANDO O FLUX DO METODO POR ELE EXECUTADO


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
