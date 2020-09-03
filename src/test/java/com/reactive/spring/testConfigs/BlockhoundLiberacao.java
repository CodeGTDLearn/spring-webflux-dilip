package com.reactive.spring.testConfigs;

import lombok.NoArgsConstructor;
import reactor.blockhound.BlockHound;

@NoArgsConstructor
public class BlockhoundLiberacao {

    static void liberarMetodos (){

        //EXCECOES DE METODOS BLOQUEANTES NO BLOCKHOUND:
        BlockHound.install(
                builder -> builder
                        .allowBlockingCallsInside("java.io.PrintStream",
                                                  "write"
                                                 )
                          );
    }

}
