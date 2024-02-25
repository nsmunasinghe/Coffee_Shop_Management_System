package com.bion.project2.bo.custom.impl;

import com.bion.project2.bo.custom.OrderBO;
import com.bion.project2.dao.DAOFactory;
import com.bion.project2.dao.custom.CustomerDAO;
import com.bion.project2.dao.custom.OrderDAO;
import com.bion.project2.dao.custom.ProductDAO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.dto.OrderDTO;
import com.bion.project2.dto.OrderInfoDTO;
import com.bion.project2.entity.*;
import com.bion.project2.enums.DAOType;

import java.sql.SQLException;
import java.util.*;

public class OrderBOImpl implements OrderBO {

    private final OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    private final CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    private final ProductDAO productDAO = DAOFactory.getInstance().getDAO(DAOType.PRODUCT);

    @Override
    public boolean saveOrder(OrderDTO oDTO, List<OrderInfoDTO> infoDTOList) throws Exception {

        Customer customer = customerDAO.find(oDTO.getCustomerID());

        if(customer!=null) {

            Set<OrderInfo> orderInfoSet = new HashSet<>();

            Order order = new Order(
                    oDTO.getOrderID(),
                    oDTO.getCustomerID(),
                    oDTO.getTotal(),
                    oDTO.getPaidAmount(),
                    oDTO.getBalance(),
                    oDTO.getDate(),
                    customer,
                    orderInfoSet
            );

            for (OrderInfoDTO infoDTO : infoDTOList) {
                OrderInfo orderInfo = new OrderInfo(
                        new OrderInfoKey(infoDTO.getOrderID(), infoDTO.getProductID()),
                        order,
                        productDAO.find(infoDTO.getProductID()),
                        infoDTO.getQuantity());

                productDAO.updateStock(infoDTO.getProductID(), infoDTO.getQuantity());
                orderInfoSet.add(orderInfo);
            }

            return orderDAO.save(order);
        }
        return false;
    } // DONE

    @Override
    public Double getTodayIncome() throws Exception {
        Double income = 0.0;

        for (Order order : orderDAO.getAllByDate()){
            income += order.getTotal();
        }
        return income;
    }

    @Override
    public Double getTotalIncome() throws Exception {
        Double total = 0.0;

        for (Order order : orderDAO.findAll()){
            total += order.getTotal();
        }

        return total;
    }

    @Override
    public Integer getSoldCount() throws Exception {
        Integer count = 0;

        for (Order order : orderDAO.findAll()){
            for(OrderInfo info : order.getOrderInfos()){
                count += info.getQuantity();
            }
        }

        return count;
    }

    @Override
    public Map<String, Integer> getSoldCountWithDescription() throws Exception {
        Map<String,Integer> productsWithSoldCounts = new HashMap<>();

        for(Order order : orderDAO.findAll()){
            for(OrderInfo info: order.getOrderInfos()){
                productsWithSoldCounts.merge(info.getProduct().getDescription(), info.getQuantity(), Integer::sum);
            }
        }

        return productsWithSoldCounts;
    }

    @Override
    public List<OrderDTO> getAllOrders() throws Exception {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order order: orderDAO.findAll()){
            orderDTOList.add(new OrderDTO(
                    order.getOrderID(),
                    order.getCustomerID(),
                    order.getTotal(),
                    order.getPaidAmount(),
                    order.getBalance(),
                    order.getDate()
            ));
        }
        return orderDTOList;
    } // Done

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateOrderId();
    } // Done
}
