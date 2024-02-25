package com.bion.project2.dao.custom;

import com.bion.project2.dao.CrudDAO;
import com.bion.project2.entity.Product;

import java.sql.SQLException;

public interface ProductDAO extends CrudDAO<Product, String> {
    String generateProductId() throws SQLException, ClassNotFoundException;

    boolean updateStock(String pID, Integer quantity) throws SQLException, ClassNotFoundException;
}
