package com.reactive.spring.controller;

import com.reactive.spring.testConfigs.ControllersConfig;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@WebFluxTest
public class StepVerifierRestController extends ControllersConfig {

    //DEFAULT: WEB-TEST-CLIENT WITH MOCK-SERVER
    @Autowired
    WebTestClient webTestClient;

    final MediaType MTYPE_JSON = MediaType.APPLICATION_JSON;

    @Before
    public void setUpLocal() {

        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofMillis(60000))
                .build();
        //REAL-SERVER(non-blocking client)  IN WEB-TEST-CLIENT:
        //webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/dilipi").build();
    }

    @Test
    public void blockHoundWorks() {
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
    public void test_StepVerifier() {
        Flux<Integer> integerFlux = webTestClient
                .get()
                .uri("/dilipi/flux")
                .accept(MTYPE_JSON)
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
    public void test_HasSize() {
        webTestClient
                .get()
                .uri("/dilipi/flux")
                .accept(MTYPE_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MTYPE_JSON)
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    public void test_AssertEquals() {

        List<Integer> expectedList = Arrays.asList(1,2,3);

        EntityExchangeResult<List<Integer>> result =
                webTestClient
                        .get()
                        .uri("/dilipi/flux")
                        .accept(MTYPE_JSON)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBodyList(Integer.class)
                        .returnResult();

        assertEquals(expectedList,result.getResponseBody());
    }

    @Test
    public void test_ConsumeWith() {

        List<Integer> expectedList = Arrays.asList(1,2,3);

        webTestClient
                .get()
                .uri("/dilipi/flux")
                .accept(MTYPE_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Integer.class)
                .consumeWith((response) -> assertEquals(expectedList,response.getResponseBody()));
    }

    @Ignore
    @Test
    public void test_Infinite() {
        Flux<Long> LongFlux = webTestClient
                .get()
                .uri("/dilipi/flux-stream-infinite")
                .accept(MTYPE_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier
                .create(LongFlux)
                .expectSubscription()
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }

    @Test
    public void test_Mono() {
        Integer expectedValue = 1;

        webTestClient
                .get()
                .uri("/dilipi/mono")
                .accept(MTYPE_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(expectedValue,response.getResponseBody());
                });
    }
}