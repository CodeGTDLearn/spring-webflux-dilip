package com.reactive.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;


@SpringBootApplication
//@EnableR2dbcRepositories
public class Application {

//    static {
//        BlockHound.install();
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class ,args);
    }

}
