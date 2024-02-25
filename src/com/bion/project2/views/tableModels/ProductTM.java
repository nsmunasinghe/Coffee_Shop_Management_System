package com.bion.project2.views.tableModels;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductTM {
    private String productID;
    private String Description;
    private String type;
    private Double unitPrice;
    private Integer stock;
    private Date date;
}
