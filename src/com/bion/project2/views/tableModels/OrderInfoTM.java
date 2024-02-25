package com.bion.project2.views.tableModels;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderInfoTM {
    private String productID;
    private String description;
    private Integer quantity;
}
