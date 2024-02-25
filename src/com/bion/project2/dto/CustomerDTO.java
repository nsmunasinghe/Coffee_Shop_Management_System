package com.bion.project2.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    private String customerID;
    private String firstName;
    private String lastName;
    private String address;
    private String contactNum;
    private Date date;
}
