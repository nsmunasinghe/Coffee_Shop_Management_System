package com.bion.project2.views.tableModels;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderTM {
    private String orderID;
    private String customerID;
    private Double total;
    private Double paidAmount;
    private Double balance;
    private Date date;
}
