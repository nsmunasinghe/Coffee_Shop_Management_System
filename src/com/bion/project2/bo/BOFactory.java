package com.bion.project2.bo;

import com.bion.project2.bo.custom.impl.*;
import com.bion.project2.enums.BOType;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance() {
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBO(BOType type) {
        switch (type) {
            case CUSTOMER   : return (T) new CustomerBOImpl();
            case EMPLOYEE   : return (T) new EmployeeBOImpl();
            case ORDER      : return (T) new OrderBOImpl();
            case ORDER_INFO : return (T) new OrderInfoBOImpl();
            case PRODUCT    : return (T) new ProductBOImpl();
            default         : return null;
        }
    }
}
