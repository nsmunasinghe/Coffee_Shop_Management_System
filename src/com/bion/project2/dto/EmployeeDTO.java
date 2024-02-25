package com.bion.project2.dto;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    private String employeeID;
    private String firstName;
    private String lastName;
    private String address;
    private String mobile;
    private String imagePath;
    private String username;
    private String password;
    private String nic;
    private String question;
    private String answer;
    private Date date;
}
