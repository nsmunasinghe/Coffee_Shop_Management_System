package com.bion.project2.dao;

import com.bion.project2.dao.custom.impl.*;
import com.bion.project2.enums.DAOType;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory==null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T> T getDAO(DAOType type){
        switch (type){
            case CUSTOMER   : return (T) new CustomerDAOImpl();
            case EMPLOYEE   : return (T) new EmployeeDAOImpl();
            case ORDER      : return (T) new OrderDAOImpl();
            case ORDER_INFO : return (T) new OrderInfoDAOImpl();
            case PRODUCT    : return (T) new ProductDAOImpl();
            default         : return null;
        }
    }
}
