package com.reactive.spring.testConfigs;

import lombok.NoArgsConstructor;
import reactor.blockhound.BlockHound;

@NoArgsConstructor
public class BlockhoundUtils {

    static void liberarMetodos() {

        //EXCECOES DE METODOS BLOQUEANTES DETECTADOS PELO BLOCKHOUND:
        BlockHound
                .install(builder -> builder
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "write"
                                                          )
                                 .allowBlockingCallsInside("java.io.FileOutputStream",
                                                           "writeBytes"
                                                          )
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "write"
                                                          )
                                 .allowBlockingCallsInside("java.io.FileOutputStream",
                                                           "writeBytes"
                                                          )
                                 .allowBlockingCallsInside("java.io.BufferedOutputStream",
                                                           "flushBuffer"
                                                          )
                                 .allowBlockingCallsInside("java.io.BufferedOutputStream",
                                                           "flush"
                                                          )
                                 .allowBlockingCallsInside("java.io.OutputStreamWriter",
                                                           "flushBuffer"
                                                          )
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "print"
                                                          )
                                 .allowBlockingCallsInside("java.io.PrintStream",
                                                           "println"
                                                          )
                        );
    }
}