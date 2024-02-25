package com.bion.project2.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private String orderID;
    private String customerID;
    private Double total;
    private Double paidAmount;
    private Double balance;
    private Date date;
}
