package com.reactive.spring;

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
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.blockhound.BlockHound;

import static org.springframework.test.annotation.DirtiesContext.ClassMode;

//*********************************************
//****        ++++ CONFLITO_01 ++++        ****
//****       RunWith X DataR2dbcTest       ****
//*********************************************
@RunWith(SpringRunner.class)
@DataR2dbcTest
//****************************************************************************

//*********************************************
//**        ++++ CONFLITO_02 ++++          ****
//** DataJpaTest X AutoConfigureTestDatabase **
//*********************************************
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//****************************************************************************

//*********************************************
//**        ++++ OBSERVATION ++++          ****
//**      SpringBootTest X WebFluxTest       **
//*********************************************
//SpringBootTest: Scan the whole ClassPath
//WebFluxTest: DOES NOT scan the whole ClassPath, thus @contextConfiguration is needed
//****************************************************************************
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebFluxTest
//****************************************************************************

@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureWebTestClient
@Slf4j
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
public class GlobalTestConfig {

    final private static String BASE_PATH = "http://localhost:8080/dilipi";
    final private static Long MAX_TIMEOUT = 15000L;
    final private static ContentType API_CONTENT_TYPE = ContentType.JSON;
    //    //    @LocalServerPort
    //    final private static int port = 8080;

    @BeforeClass
    public static void setUp() {
        //substitue os ".log().And()." em todos os REstAssureTestes
        //                RestAssuredWebTestClient.enableLoggingOfRequestAndResponseIfValidationFails();
        //                RestAssuredWebTestClient.config = new RestAssuredWebTestClientConfig().logConfig(LogDetail.BODY);

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

        BlockHound.install(
                //                builder -> builder.allowBlockingCallsInside("java.util.UUID","randomUUID")
                          );
    }

    @AfterClass
    public static void tearDown() {
        //        DELETE AO TOKEN AFTER ALL TESTS
        //        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
        //        req.removeHeader("Autorization");

        RestAssuredWebTestClient.reset();
    }
}




