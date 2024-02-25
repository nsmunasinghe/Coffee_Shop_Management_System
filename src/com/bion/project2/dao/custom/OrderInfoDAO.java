package com.bion.project2.dao.custom;

import com.bion.project2.dao.CrudDAO;
import com.bion.project2.entity.OrderInfo;

import java.util.List;
import java.util.Set;

public interface OrderInfoDAO extends CrudDAO<OrderInfo, String> {
    Set<OrderInfo> findByOrderID(String orderID) throws Exception;
}
