package com.bion.project2.bo.custom;

import com.bion.project2.bo.SuperBO;
import com.bion.project2.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    String generateCustomerId() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO cDTO) throws Exception;

    CustomerDTO findCustomer(String id) throws Exception;

    List<CustomerDTO> findAllCustomers() throws Exception;

    CustomerDTO findByContactNum(String contactNum)throws Exception;
}
