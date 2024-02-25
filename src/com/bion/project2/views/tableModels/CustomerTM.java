package com.bion.project2.views.tableModels;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTM {
    private String customerID;
    private String firstName;
    private String lastName;
    private String address;
    private String contactNum;
    private Date date;
}
