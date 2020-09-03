package com.reactive.spring.controller;

import com.reactive.spring.testConfigs.ControllersConfig;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.http.HttpStatus.OK;

public class RestAssureRestController extends ControllersConfig {

    //WEB-TEST-CLIENT(non-blocking client) WITH MOCK-SERVER
    @Autowired
    WebTestClient webTestClient;

    @Before
    public void setUpLocal() {
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
    public void test_RestAssuredWebTestClient() {
        RestAssuredWebTestClient
                .given()
                .webTestClient(webTestClient)
                .header("Accept",ContentType.ANY)
                .header("Content-type",ContentType.JSON)

                .when()
                .get("flux")

                .then()
                .statusCode(OK.value())
                .log()
                .headers()
                .and()
                .log()
                .body()
                .and()

                .body(containsString("1"))
                .body(stringContainsInOrder("1","2","3"))
        ;
    }
}