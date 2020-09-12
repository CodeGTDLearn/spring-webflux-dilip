package com.reactive.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Table("items")
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ItemCapped {

    @Id
    private String id;
    private String description;
    private Double price;

}
