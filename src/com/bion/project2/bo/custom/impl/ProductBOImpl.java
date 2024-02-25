package com.bion.project2.bo.custom.impl;

import com.bion.project2.bo.custom.ProductBO;
import com.bion.project2.dao.DAOFactory;
import com.bion.project2.dao.custom.ProductDAO;
import com.bion.project2.dto.ProductDTO;
import com.bion.project2.entity.Product;
import com.bion.project2.enums.DAOType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    private final ProductDAO productDAO = DAOFactory.getInstance().getDAO(DAOType.PRODUCT);

    @Override
    public boolean saveProduct(ProductDTO pDTO) throws Exception {
        return productDAO.save(new Product(
                pDTO.getProductID(),
                pDTO.getDescription(),
                pDTO.getType(),
                pDTO.getUnitPrice(),
                pDTO.getStock(),
                pDTO.getImagePath(),
                pDTO.getDate()
        ));
    } // Done

    @Override
    public boolean updateProduct(ProductDTO pDTO) throws Exception {
        return productDAO.update(new Product(
                pDTO.getProductID(),
                pDTO.getDescription(),
                pDTO.getType(),
                pDTO.getUnitPrice(),
                pDTO.getStock(),
                pDTO.getImagePath(),
                pDTO.getDate()
        ));
    } // Done

    @Override
    public ProductDTO findProduct(String id) throws Exception {
        Product product = productDAO.find(id);

        return new ProductDTO(
                product.getProductID(),
                product.getDescription(),
                product.getType(),
                product.getUnitPrice(),
                product.getStock(),
                product.getImagePath(),
                product.getDate()
        );
    } // Done

    @Override
    public List<ProductDTO> findAllProducts() throws Exception {

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product: productDAO.findAll()){
            productDTOList.add(new ProductDTO(
                    product.getProductID(),
                    product.getDescription(),
                    product.getType(),
                    product.getUnitPrice(),
                    product.getStock(),
                    product.getImagePath(),
                    product.getDate()
            ));
        }
        return productDTOList;
    } // Done

    @Override
    public boolean deleteProduct(String id) throws Exception {
        return productDAO.delete(id);
    } // Done

    @Override
    public boolean isProductExist(String id) throws Exception {
        Product product = productDAO.find(id);
        return product != null;
    } // Done

    @Override
    public Integer getStock(String productID) throws Exception {
        Product product = productDAO.find(productID);

        if (product!=null){
            return product.getStock();
        }
        return 0;
    }

    @Override
    public String generateProductId() throws SQLException, ClassNotFoundException {
        return productDAO.generateProductId();
    } // DONE
}
