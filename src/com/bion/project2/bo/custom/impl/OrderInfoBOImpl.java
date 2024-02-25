package com.bion.project2.bo.custom.impl;

import com.bion.project2.bo.custom.OrderInfoBO;
import com.bion.project2.dao.DAOFactory;
import com.bion.project2.dao.custom.OrderInfoDAO;
import com.bion.project2.entity.OrderInfo;
import com.bion.project2.enums.DAOType;
import com.bion.project2.views.tableModels.OrderInfoTM;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoBOImpl implements OrderInfoBO {
    private final OrderInfoDAO orderInfoDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER_INFO);

    @Override
    public List<OrderInfoTM> getOrderInfo(String orderID) throws Exception {
        List<OrderInfoTM> orderInfoTMList = new ArrayList<>();

        for (OrderInfo oInfo : orderInfoDAO.findByOrderID(orderID)){
            orderInfoTMList.add(new OrderInfoTM(
                    oInfo.getId().getProductId(),
                    oInfo.getProduct().getDescription(),
                    oInfo.getQuantity()
            ));
        }
        return orderInfoTMList;
    }
}
