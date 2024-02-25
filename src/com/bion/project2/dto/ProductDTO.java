package com.bion.project2.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
    private String productID;
    private String description;
    private String type;
    private Double unitPrice;
    private Integer stock;
    private String imagePath;
    private Date date;
}
