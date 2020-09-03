package com.reactive.spring.repo;

import com.reactive.spring.entities.Item;
import com.reactive.spring.testConfigs.MongoRepoConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithDescPrice;
import static com.reactive.spring.databuilder.ObjectMotherItem.newItemWithIdDescPrice;

public class ItemReactiveRepoMongoTest extends MongoRepoConfig {

    private List<Item> itemList;
    private Item itemTest;

    @Autowired
    ItemReactiveRepoMongo repo;

    @Before
    public void setUpLocal() {

        itemTest = newItemWithIdDescPrice("ABC").create();

        itemList = Arrays.asList(newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 newItemWithDescPrice().create(),
                                 itemTest
                                );

        repo.deleteAll()
            .thenMany(Flux.fromIterable(itemList))
            .flatMap(repo::save)
            .doOnNext((item -> System.out.println(" Inserted item is: " + item)))
            .blockLast(); // THATS THE WHY, BLOCKHOUND IS NOT BEING USED.
    }

//    @Ignore
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
    public void getAllTest() {

        StepVerifier
                .create(repo.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void getById() {
        StepVerifier
                .create(repo.findById("ABC"))
                .expectSubscription()
                .expectNextMatches(item -> item.getDescription()
                                               .equals(itemTest.getDescription()))
                .verifyComplete();
    }

    @Test
    public void getItemByDescription() {
        itemTest.setDescription("AAAAA");
        StepVerifier
                .create(repo.findByDescription(itemTest.getDescription())
                            .log("getItemDescription: "))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void saveItem() {
        Mono<Item> savedItem = repo.save(itemTest);
        StepVerifier.create(savedItem.log("savedItem : "))
                    .expectSubscription()
                    .expectNextMatches(item -> (
                            item.getId() != null && item.getDescription()
                                                        .equals(itemTest.getDescription())))
                    .verifyComplete();
    }

    @Test
    public void updateItem() {
        double newPrice = 520.00;
        Mono<Item> updatedItem =
                repo
                        .findByDescription(itemTest.getDescription())
                        .map(itemFound -> {
                            itemFound.setPrice(newPrice);
                            return itemFound;
                        })
                        .flatMap(itemToBeUpdated -> repo.save(itemToBeUpdated));

        StepVerifier
                .create(updatedItem)
                .expectSubscription()
                .expectNextMatches(item -> item.getPrice() == newPrice)
                .verifyComplete();

    }

    @Test
    public void deleteItemById() {
        Mono<Void> deletedItem =
                repo.findById(itemTest.getId())
                .map(Item::getId)
                .flatMap(id -> repo.deleteById(id));

        StepVerifier
                .create(deletedItem.log())
                .expectSubscription()
                .verifyComplete();

        StepVerifier
                .create(repo.findAll().log("The new item list : "))
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }


}