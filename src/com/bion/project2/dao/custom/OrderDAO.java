package com.bion.project2.dao.custom;

import com.bion.project2.dao.CrudDAO;
import com.bion.project2.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order, String> {

    List<Order> getAllByDate() throws Exception;

    String generateOrderId() throws SQLException, ClassNotFoundException;
}
