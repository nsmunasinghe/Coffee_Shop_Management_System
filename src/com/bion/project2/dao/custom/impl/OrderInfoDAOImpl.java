package com.bion.project2.dao.custom.impl;

import com.bion.project2.HibernateUtil;
import com.bion.project2.dao.custom.OrderInfoDAO;
import com.bion.project2.entity.Order;
import com.bion.project2.entity.OrderInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class OrderInfoDAOImpl implements OrderInfoDAO {
    @Override
    public boolean save(OrderInfo orderInfo) throws Exception {
        return false;
    }

    @Override
    public boolean update(OrderInfo orderInfo) throws Exception {
        return false;
    }

    @Override
    public OrderInfo find(String id) throws Exception {
        return null;
    }

    @Override
    public List<OrderInfo> findAll() throws Exception {
        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public Set<OrderInfo> findByOrderID(String orderID) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Order order = session.get(Order.class, orderID);
        Set<OrderInfo> orderInfoSet = order.getOrderInfos();

        transaction.commit();
        session.close();

        return orderInfoSet;
    }
}
