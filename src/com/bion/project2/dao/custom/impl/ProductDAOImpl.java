package com.bion.project2.dao.custom.impl;

import com.bion.project2.HibernateUtil;
import com.bion.project2.dao.custom.ProductDAO;
import com.bion.project2.entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public boolean save(Product product) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(product);

        transaction.commit();
        session.close();

        return true;
    } // DONE

    @Override
    public boolean update(Product product) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(product);

        transaction.commit();
        session.close();

        return true;
    } // DONE

    @Override
    public Product find(String id) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Product product = session.find(Product.class, id);

        transaction.commit();
        session.close();

        return product;
    } // DONE

    @Override
    public List<Product> findAll() throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Product");
        List<Product> productList = query.list();

        transaction.commit();
        session.close();

        return productList;
    } // DONE

    @Override
    public boolean delete(String id) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(session.find(Product.class, id));

        transaction.commit();
        session.close();

        return true;
    } // DONE

    @Override
    public boolean updateStock(String pID, Integer quantity) throws SQLException, ClassNotFoundException{
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Product WHERE productID=:id");
        query.setParameter("id",pID);

        Product product = (Product) query.uniqueResult();

        if(product!=null){
            product.setStock(product.getStock() - quantity);
        }

        transaction.commit();
        session.close();

        return true;
    } // DONE

    @Override
    public String generateProductId() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Query query = session.createQuery("FROM Product ORDER BY productID DESC");

        List<Product> productList = query.list();

        session.close();

        if(!productList.isEmpty()){
            String lastID = productList.get(0).getProductID();

            try {
                String id = lastID.split("[A-Z]")[1];
                int newID = Integer.parseInt(id) + 1;
                lastID = String.format("P%03d", newID);
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
            return lastID;
        }
        return "P001";
    } // DONE
}
