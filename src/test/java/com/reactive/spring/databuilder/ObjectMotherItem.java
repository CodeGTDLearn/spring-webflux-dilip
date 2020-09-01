package com.reactive.spring.databuilder;

import com.github.javafaker.Faker;
import com.reactive.spring.entities.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class ObjectMotherItem {

    private Item item;

    private static Faker faker = new Faker(new Locale("en-CA.yml"));

    public static ObjectMotherItem newItemWithDescPrice() {
        // @formatter:off
        Item fakeItem = new Item();
        fakeItem.setDescription(faker.beer().name());
        fakeItem.setPrice(faker.random().nextDouble());
        return ObjectMotherItem.builder().item(fakeItem).build();
        // @formatter:on
    }
    public static ObjectMotherItem newItemWithIdDescPrice(String id) {
        // @formatter:off
        Item fakeItem = new Item();
        fakeItem.setId(id);
        fakeItem.setDescription(faker.beer().name());
        fakeItem.setPrice(faker.random().nextDouble());
        return ObjectMotherItem.builder().item(fakeItem).build();
        // @formatter:on
    }

    public Item create() {
        return this.item;
    }
}


