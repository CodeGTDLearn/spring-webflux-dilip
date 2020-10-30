package com.reactive.spring.testConfigs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

//*********************************************
//**        ++++ OBSERVATION 01 ++++         **
//**      SpringBootTest X WebFluxTest       **
//*********************************************
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebFluxTest
//****************************************************************************
//@WebFluxTest
//a) SpringBootTest: Scan the whole ClassPath
//
//b) WebFluxTest: DONT scan CLASSPATH, USAR @contextConfiguration
//       POSSIVEL ERRO: Unsatisfied dependency expressed through field 'webTestClient'
//****************************************************************************

//*********************************************
//**      ++++ GENERAL CONFIGS ++++          **
//**      SpringBootTest X WebFluxTest       **
//*********************************************
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@Slf4j

//*********************************************
//**        ++++ DIRTIESCONTEXT ++++        **
//*********************************************
// @DirtiesContext OPTIONS, such as:
// a) DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
// WILL INVALIDATE BlockHoundallowBlockingCallsInside
// RESULTING IN:
// a) reactor.core.Exceptions$ReactiveException: reactor.blockhound.BlockingOperationError: Blocking call!
@DirtiesContext
@Ignore
public class HandlerConfig {

    final private static String BASE_PATH = "http://localhost:8080/dilipi";
    final private static Long MAX_TIMEOUT = 15000L;
    final private static ContentType API_CONTENT_TYPE = ContentType.JSON;

    @BeforeClass
    public static void setUp() {
        //DEFINE CONFIG-GLOBAL PARA OS REQUESTS DOS TESTES
        RestAssuredWebTestClient.requestSpecification =
                new WebTestClientRequestSpecBuilder()
                        .setContentType(API_CONTENT_TYPE)
                        .setBasePath(BASE_PATH)
                        .build();

        //DEFINE CONFIG-GLOBAL PARA OS RESPONSE DOS TESTES
        RestAssuredWebTestClient.responseSpecification =
                new ResponseSpecBuilder()
                        .expectResponseTime(
                                Matchers.lessThanOrEqualTo(MAX_TIMEOUT))
                        .expectContentType(API_CONTENT_TYPE)
                        .build();
        BlockhoundUtils.liberarMetodos();
    }

    @AfterClass
    public static void tearDown() {
        RestAssuredWebTestClient.reset();
    }
}