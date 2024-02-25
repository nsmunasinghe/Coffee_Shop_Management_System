package com.bion.project2.dao.custom.impl;

import com.bion.project2.HibernateUtil;
import com.bion.project2.dao.custom.CustomerDAO;
import com.bion.project2.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer customer) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(customer);

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public boolean update(Customer customer) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(customer);

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public Customer find(String id) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.find(Customer.class, id);

        transaction.commit();
        session.close();

        return customer;
    } // Done

    @Override
    public List<Customer> findAll() throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Customer"); // Define The Query

        List<Customer> customerList = query.list(); // Execute The Query

        transaction.commit();
        session.close();

        return customerList;
    } // Done

    @Override
    public boolean delete(String id) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(session.find(Customer.class, id));

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public Customer findByContactNum(String contactNum) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Customer WHERE contactNum=:number");
        query.setParameter("number", contactNum);

        Customer customer = (Customer) query.uniqueResult();

        transaction.commit();
        session.close();

        return customer;
    } // Done

    @Override
    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Query query = session.createQuery("FROM Customer ORDER BY customerID DESC");

        List<Customer> customerList = query.list();

        session.close();

        if(!customerList.isEmpty()){
            String lastID = customerList.get(0).getCustomerID();

            try {
                String id = lastID.split("[A-Z]")[1];
                int newID = Integer.parseInt(id) + 1;
                lastID = String.format("C%03d", newID);
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
            return lastID;
        }
        return "C001";
    } // Done
}
