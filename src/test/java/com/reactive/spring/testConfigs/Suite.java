package com.reactive.spring.testConfigs;

import com.reactive.spring.controller.RestAssureRestController;
import com.reactive.spring.controller.StepVerifierRestController;
import com.reactive.spring.repo.ItemReactiveRepoMongoRepo;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        ItemReactiveRepoMongoRepo.class,
        StepVerifierRestController.class,
        RestAssureRestController.class
})
public class Suite {
//    @Before
//    public void setUp() {
//        System.out.println("----- SUPER SETUP -----");
//    }
//
//    @After
//    public void tearDown() {
//        System.out.println("----- SUPER TEARDOWN -----");
//    }
}
