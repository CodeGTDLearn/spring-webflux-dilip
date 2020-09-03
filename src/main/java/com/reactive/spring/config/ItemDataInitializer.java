package com.reactive.spring.config;

import com.reactive.spring.entities.Item;
import com.reactive.spring.repo.ItemReactiveRepoMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
public class ItemDataInitializer implements CommandLineRunner {

    @Autowired
    ItemReactiveRepoMongo repo;


    @Override
    public void run(String... args) {
        initialDataSetupLoadDataInMongoDb();
    }


    private List<Item> loadData() {

        return Arrays.asList(
                new Item(null," Samsung TV",100.00),
                new Item(null," LG TV",200.00),
                new Item(null," Phillips TV",300.00),
                new Item("ABC"," ViewSonic TV",400.00)
                            );

    }

    private void initialDataSetupLoadDataInMongoDb() {

        repo.deleteAll()
            .thenMany(Flux.fromIterable(loadData()))
            .flatMap(repo::save)
            .thenMany(repo.findAll())
            .subscribe(item -> System.out.println("Item Inserted via CLR: " + item));

    }

}
