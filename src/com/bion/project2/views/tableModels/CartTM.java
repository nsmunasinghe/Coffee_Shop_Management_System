package com.bion.project2.views.tableModels;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartTM {
    private String productID;
    private String description;
    private Integer quantity;
    private Double cost;
}
