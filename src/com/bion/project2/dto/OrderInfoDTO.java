package com.bion.project2.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderInfoDTO {
    private String orderID;
    private String productID;
    private Integer quantity;
}
