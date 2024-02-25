package com.bion.project2.dao.custom.impl;

import com.bion.project2.HibernateUtil;
import com.bion.project2.dao.custom.OrderDAO;
import com.bion.project2.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TemporalType;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean save(Order order) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(order);

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public boolean update(Order order) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(order);

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public Order find(String id) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Order order = session.find(Order.class, id);

        transaction.commit();
        session.close();

        return order;
    } // Done

    @Override
    public List<Order> findAll() throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Order");

        List<Order> orderList = query.list();

        transaction.commit();
        session.close();

        return orderList;
    } // Done

    @Override
    public boolean delete(String id) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(session.find(Order.class, id));

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public List<Order> getAllByDate() throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Date today = new Date();

        Query query = session.createQuery("FROM Order WHERE DATE(date)=:d");
        query.setParameter("d",today, TemporalType.DATE);

        List<Order> orderList = query.list();

        transaction.commit();
        session.close();

        return orderList;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Query query = session.createQuery("FROM Order ORDER BY orderID DESC");

        List<Order> orderList = query.list();

        session.close();

        if(!orderList.isEmpty()){
            String lastID = orderList.get(0).getOrderID();

            try {
                String id = lastID.split("[A-Z]")[1];
                int newID = Integer.parseInt(id) + 1;
                lastID = String.format("B%03d", newID);
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
            return lastID;
        }
        return "B001";
    } // Done
}
