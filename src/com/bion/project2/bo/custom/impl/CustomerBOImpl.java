package com.bion.project2.bo.custom.impl;

import com.bion.project2.bo.custom.CustomerBO;
import com.bion.project2.dao.DAOFactory;
import com.bion.project2.dao.custom.CustomerDAO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.entity.Customer;
import com.bion.project2.enums.DAOType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO cDTO) throws Exception {
        return customerDAO.save(new Customer(
                cDTO.getCustomerID(),
                cDTO.getFirstName(),
                cDTO.getLastName(),
                cDTO.getAddress(),
                cDTO.getContactNum(),
                cDTO.getDate()
        ));
    } // Done

    @Override
    public CustomerDTO findCustomer(String id) throws Exception {
        Customer customer = customerDAO.find(id);

        return new CustomerDTO(
                customer.getCustomerID(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getContactNum(),
                customer.getDate()
        );
    } // Done

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        for(Customer customer: customerDAO.findAll()){
            customerDTOList.add(new CustomerDTO(
                    customer.getCustomerID(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getContactNum(),
                    customer.getDate()
            ));
        }
        return customerDTOList;
    } // Done

    @Override
    public CustomerDTO findByContactNum(String contactNum) throws Exception {
        Customer customer = customerDAO.findByContactNum(contactNum);

        if (customer!=null) {
            return new CustomerDTO(
                    customer.getCustomerID(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getAddress(),
                    customer.getContactNum(),
                    customer.getDate()
            );
        }
        return null;
    } // Done

    @Override
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.generateCustomerId();
    } // Done
}
