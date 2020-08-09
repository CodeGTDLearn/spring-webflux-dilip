//package com.reactive.spring;
//
////REST-ASSURE: MODULOS COMUNS
//
//import io.restassured.builder.ResponseSpecBuilder;
//import io.restassured.http.ContentType;
//import io.restassured.module.webtestclient.RestAssuredWebTestClient;
//import io.restassured.module.webtestclient.specification.WebTestClientRequestSpecBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.hamcrest.Matchers;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Ignore;
//import org.junit.jupiter.api.Disabled;
//import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.event.annotation.AfterTestClass;
//import org.springframework.test.context.event.annotation.BeforeTestClass;
//import reactor.blockhound.BlockHound;
//
//import static org.springframework.test.annotation.DirtiesContext.ClassMode;
//
////------CONFLITO: SpringRunner X WebFluxTest---------------------------
////@RunWith(SpringRunner.class)
////--CONFLITANTES:-----------
//@WebFluxTest
//@DataR2dbcTest
////----------------------------------------------
////--JPA-CONFLITO GERAL---------------------
////@DataJpaTest
////@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
////----------------------------------------------
//@AutoConfigureWebTestClient
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//@TestPropertySource("classpath:application-test.properties")
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
////@Ignore
//@Disabled
//@ActiveProfiles("test")
//public class GlobalTestConfig {
//
//    final private static String baseUri = "http://localhost:8080/animes";
//    final private static Long MAX_TIMEOUT = 15000L;
//    final private static ContentType API_CONTENT_TYPE = ContentType.JSON;
//    //    @LocalServerPort
//    final private static int port = 8080;
//
//    @BeforeClass
//    public static void setUp() {
//
//        //substitue os ".log().And()." em todos os REstAssureTestes
////        RestAssuredWebTestClient.enableLoggingOfRequestAndResponseIfValidationFails();
////        RestAssuredWebTestClient.config = new RestAssuredWebTestClientConfig().logConfig(LogDetail.BODY);
//
//        //DEFINE CONFIG-GLOBAL PARA OS REQUESTS DOS TESTES
//        RestAssuredWebTestClient.requestSpecification =
//                new WebTestClientRequestSpecBuilder()
//                        .setContentType(API_CONTENT_TYPE)
//                        .build();
//
//        //DEFINE CONFIG-GLOBAL PARA OS RESPONSE DOS TESTES
//        RestAssuredWebTestClient.responseSpecification =
//                new ResponseSpecBuilder()
//                        .expectResponseTime(
//                                Matchers.lessThanOrEqualTo(MAX_TIMEOUT)).build();
//
//        BlockHound.install(
//                //builder -> builder.allowBlockingCallsInside("java.util.UUID" ,"randomUUID")
//        );
//    }
//
//    @AfterClass
//    public static void tearDown() {
////        DELETE AO TOKEN AFTER ALL TESTS
////        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
////        req.removeHeader("Autorization");
//
//        RestAssuredWebTestClient.reset();
//    }
//}
//
//
//
//
