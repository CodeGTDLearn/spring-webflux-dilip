package com.reactive.spring.handler;

import com.reactive.spring.router.PlaygroundRouter;
import com.reactive.spring.testConfigs.HandlerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {PlaygroundRouter.class,PlaygroundHandler.class})
public class Handler extends HandlerConfig {

    //DEFAULT: WEB-TEST-CLIENT WITH MOCK-SERVER
    @Autowired
    WebTestClient client;

    @Before
    public void setUpLocal() {
        //ADDING REAL-SERVER IN WEB-TEST-CLIENT:
        //webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/dilipi")
        // .build();
    }

    @Ignore
    @Test
    public void bHWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });

            Schedulers.parallel()
                      .schedule(task);

            task.get(10,TimeUnit.SECONDS);
            Assert.fail("should fail");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            Assert.assertTrue("detected",e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    public void flux() {
        Flux<Integer> integerFlux = client
                .get()
                .uri("/functional/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier
                .create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    public void mono() {
        Integer expectedValue = 1;

        client
                .get()
                .uri("/functional/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expectedValue,response.getResponseBody());
                });
    }
}