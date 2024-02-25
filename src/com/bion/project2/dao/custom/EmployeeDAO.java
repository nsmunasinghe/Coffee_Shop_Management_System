package com.bion.project2.dao.custom;

import com.bion.project2.dao.CrudDAO;
import com.bion.project2.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee, String> {
    Employee findByUserName(String username) throws Exception;

    String generateEmployeeID() throws SQLException, ClassNotFoundException;

}
