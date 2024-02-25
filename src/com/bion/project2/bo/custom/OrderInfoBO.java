package com.bion.project2.bo.custom;

import com.bion.project2.bo.SuperBO;
import com.bion.project2.dto.OrderInfoDTO;
import com.bion.project2.views.tableModels.OrderInfoTM;

import java.util.List;

public interface OrderInfoBO extends SuperBO {
    List<OrderInfoTM> getOrderInfo(String orderID) throws Exception;
}
