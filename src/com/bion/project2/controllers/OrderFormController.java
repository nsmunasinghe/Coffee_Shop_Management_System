package com.bion.project2.controllers;

import com.bion.project2.bo.BOFactory;
import com.bion.project2.bo.custom.CustomerBO;
import com.bion.project2.bo.custom.OrderBO;
import com.bion.project2.bo.custom.OrderInfoBO;
import com.bion.project2.bo.custom.ProductBO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.dto.OrderDTO;
import com.bion.project2.enums.BOType;
import com.bion.project2.views.tableModels.OrderInfoTM;
import com.bion.project2.views.tableModels.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    @FXML
    private TableColumn<OrderTM, String> col_OrderID;

    @FXML
    private TableColumn<OrderTM, String> col_CustomerID;

    @FXML
    private TableColumn<OrderTM, String> col_Total;

    @FXML
    private TableColumn<OrderTM, String> col_PaidAmount;

    @FXML
    private TableColumn<OrderTM, String> col_Balance;

    @FXML
    private TableColumn<OrderTM, String> col_Date;

    @FXML
    private TableColumn<OrderInfoTM, String> col_ProductID;

    @FXML
    private TableColumn<OrderInfoTM, String> col_Description;

    @FXML
    private TableColumn<OrderInfoTM, String> col_Quantity;

    @FXML
    private Label lbl_Address;

    @FXML
    private Label lbl_ContactNo;

    @FXML
    private Label lbl_CustomerName;

    @FXML
    private TableView<OrderTM> tbl_Orders;

    @FXML
    private TableView<OrderInfoTM> tbl_OrderInfo;

    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);
    private final OrderInfoBO orderInfoBO = BOFactory.getInstance().getBO(BOType.ORDER_INFO);
    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER);
    private final ProductBO productBO = BOFactory.getInstance().getBO(BOType.PRODUCT);

    @FXML
    void loadOrderTblData() {
        ObservableList<OrderTM> orderTMList = FXCollections.observableArrayList();

        try {
            for (OrderDTO oDTO : orderBO.getAllOrders()) {
                orderTMList.add(new OrderTM(
                        oDTO.getOrderID(),
                        oDTO.getCustomerID(),
                        oDTO.getTotal(),
                        oDTO.getPaidAmount(),
                        oDTO.getBalance(),
                        oDTO.getDate()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbl_Orders.setItems(orderTMList);
    } // Done

    @FXML
    void setCustomerDetails(String customerID){
        try {
            CustomerDTO cDTO = customerBO.findCustomer(customerID);

            lbl_CustomerName.setText(cDTO.getFirstName()+" "+cDTO.getLastName());
            lbl_Address.setText(cDTO.getAddress());
            lbl_ContactNo.setText(cDTO.getContactNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // Done

    @FXML
    void setOrderInfoTblData(String orderId) {
        ObservableList<OrderInfoTM> orderInfoTMList = FXCollections.observableArrayList();

        try {
            List<OrderInfoTM> list =  orderInfoBO.getOrderInfo(orderId);

            orderInfoTMList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbl_OrderInfo.setItems(orderInfoTMList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //-------> Order Table <---------------------------------------
        col_OrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        col_CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_PaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        col_Balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadOrderTblData();

        //-------> Order Info Table <---------------------------------------
        col_ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tbl_Orders.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue!=null){
                        setOrderInfoTblData(newValue.getOrderID());
                        setCustomerDetails(newValue.getCustomerID());
                    }
                });


    }
}
