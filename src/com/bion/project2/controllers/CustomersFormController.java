package com.bion.project2.controllers;

import com.bion.project2.bo.BOFactory;
import com.bion.project2.bo.custom.CustomerBO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.enums.BOType;
import com.bion.project2.views.tableModels.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CustomersFormController implements Initializable {

    @FXML
    private TableColumn<CustomerTM, String> col_Address;

    @FXML
    private TableColumn<CustomerTM, String> col_ContactNo;

    @FXML
    private TableColumn<CustomerTM, String> col_CustomerID;

    @FXML
    private TableColumn<CustomerTM, String> col_Date;

    @FXML
    private TableColumn<CustomerTM, String> col_FirstName;

    @FXML
    private TableColumn<CustomerTM, String> col_LastName;

    @FXML
    private TableView<CustomerTM> tbl_Customer;

    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER);
    public void loadTableData() {
        ObservableList<CustomerTM> customerTMList = FXCollections.observableArrayList();

        try {
            for (CustomerDTO cDTO : customerBO.findAllCustomers()){
                customerTMList.add(new CustomerTM(
                        cDTO.getCustomerID(),
                        cDTO.getFirstName(),
                        cDTO.getLastName(),
                        cDTO.getAddress(),
                        cDTO.getContactNum(),
                        cDTO.getDate()
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tbl_Customer.setItems(customerTMList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        col_FirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_LastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_ContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNum"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTableData();
    }
}
