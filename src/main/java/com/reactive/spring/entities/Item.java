package com.reactive.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
//@Table("items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private Integer id;
    private String description;
    private Double price;

}
