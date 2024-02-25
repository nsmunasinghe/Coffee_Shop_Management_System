package com.bion.project2.bo.custom;

import com.bion.project2.bo.SuperBO;
import com.bion.project2.dto.EmployeeDTO;
import com.bion.project2.entity.Employee;

import java.sql.SQLException;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDTO dto) throws Exception;

    EmployeeDTO getEmployee(String username) throws Exception;

    boolean isPasswordChanged(String username, String password) throws Exception;

    boolean isValidLogin(String username, String password) throws Exception;

    boolean isUserExist(String username) throws Exception;

    boolean isSecretAnswerCorrect(String username, String question, String answer) throws Exception;

    String generateEmployeeID() throws SQLException, ClassNotFoundException;
}
