package com.bion.project2.dao.custom;

import com.bion.project2.dao.CrudDAO;
import com.bion.project2.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer, String> {
    String generateCustomerId() throws SQLException, ClassNotFoundException;
    Customer findByContactNum(String contactNum) throws Exception;
}
