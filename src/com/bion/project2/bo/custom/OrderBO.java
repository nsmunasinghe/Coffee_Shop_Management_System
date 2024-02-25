package com.bion.project2.bo.custom;

import com.bion.project2.bo.SuperBO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.dto.OrderDTO;
import com.bion.project2.dto.OrderInfoDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderBO extends SuperBO {

    boolean saveOrder(OrderDTO oDTO, List<OrderInfoDTO> details) throws Exception;

    List<OrderDTO> getAllOrders() throws Exception;

    Double getTodayIncome() throws Exception;

    Double getTotalIncome() throws Exception;

    Integer getSoldCount() throws Exception;

    Map<String, Integer> getSoldCountWithDescription() throws  Exception;

    String generateOrderId() throws SQLException, ClassNotFoundException;
}
