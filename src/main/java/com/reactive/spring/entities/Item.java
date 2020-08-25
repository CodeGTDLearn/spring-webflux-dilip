package com.reactive.spring.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private Integer id;
    private String description;
    private Double price;

}
