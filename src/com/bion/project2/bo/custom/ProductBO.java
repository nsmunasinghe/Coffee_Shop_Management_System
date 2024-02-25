package com.bion.project2.bo.custom;

import com.bion.project2.bo.SuperBO;
import com.bion.project2.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductBO extends SuperBO {

    boolean saveProduct(ProductDTO dto) throws Exception;

    boolean updateProduct(ProductDTO dto) throws Exception;

    ProductDTO findProduct(String id) throws Exception;

    List<ProductDTO> findAllProducts() throws Exception;

    boolean deleteProduct(String id) throws Exception;

    boolean isProductExist(String id) throws Exception;

    Integer getStock(String productID) throws Exception;

    String generateProductId() throws SQLException, ClassNotFoundException;
}
