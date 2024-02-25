package com.bion.project2.controllers;

import com.bion.project2.bo.BOFactory;
import com.bion.project2.bo.custom.CustomerBO;
import com.bion.project2.bo.custom.OrderBO;
import com.bion.project2.bo.custom.ProductBO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.dto.OrderDTO;
import com.bion.project2.dto.OrderInfoDTO;
import com.bion.project2.dto.ProductDTO;
import com.bion.project2.entity.OrderInfoKey;
import com.bion.project2.entity.Product;
import com.bion.project2.enums.BOType;
import com.bion.project2.views.tableModels.CartTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private GridPane cardsGridPane;

    @FXML
    private TableColumn<CartTM, String> col_Cost;

    @FXML
    private TableColumn<CartTM, String> col_Description;

    @FXML
    private TableColumn<CartTM, String> col_ID;

    @FXML
    private TableColumn<CartTM, String> col_Qty;

    @FXML
    private Label lbl_Balance;

    @FXML
    private Label lbl_OrderID;

    @FXML
    private Label lbl_total;

    @FXML
    private TableView<CartTM> tbl_Cart;

    @FXML
    private TextField txt_Address;

    @FXML
    private TextField txt_Amount;

    @FXML
    private TextField txt_CustomerID;

    @FXML
    private TextField txt_FirstName;

    @FXML
    private TextField txt_LastName;

    @FXML
    private TextField txt_Mobile;

    private final ProductBO productBO = BOFactory.getInstance().getBO(BOType.PRODUCT);
    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER);
    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);

    private final ObservableList<ProductDTO> cardList = FXCollections.observableArrayList();
    private ObservableList<CartTM> cart = FXCollections.observableArrayList();
    private final List<OrderInfoDTO> orderInfoDTOList = new ArrayList<>();

    private double total;
    private double balance;
    private int totalQty = 0;

    private CartTM selectedRow;

    private CustomerDTO cDTO;

    @FXML
    void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.setTitle(alertType+" message");
        alert.setHeaderText("");
        alert.setContentText("");
        alert.show();
    }

    public void displayAllCardsOnGrid() {

        cardList.clear();
        try {
            cardList.addAll(productBO.findAllProducts());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cardsGridPane.getChildren().clear();
        cardsGridPane.getRowConstraints().clear();
        cardsGridPane.getColumnConstraints().clear();

        int gridRow = 0;
        int gridColumn = 0;

        for (ProductDTO productDTO : cardList) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../views/ProductCard.fxml"));

                AnchorPane cardPane = loader.load();

                ProductCardController cardCon = loader.getController();
                cardCon.setData(productDTO);

                if (gridColumn == 3) {
                    gridColumn = 0;
                    gridRow += 1;
                }

                cardsGridPane.add(cardPane, gridColumn++, gridRow);

                GridPane.setMargin(cardPane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void getTotal() {

        Double tempTotal = 0.0;

        for(CartTM cartTM: cart){
            tempTotal += cartTM.getCost();
        }

        DecimalFormat dFormat = new DecimalFormat("#.##");
        String roundedNumber = dFormat.format(tempTotal);

        total = Double.parseDouble(roundedNumber);

        lbl_total.setText("$ " + total);
    }

    @FXML
    void getBalance() {
        if(txt_Amount.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter the Amount").show();
        }else{
            balance = 0;
            lbl_Balance.setText("LKR 0.00");

            if(Double.parseDouble(txt_Amount.getText()) < total){
                new Alert(Alert.AlertType.ERROR, "Insufficient Amount.\nPlease enter the Amount Again").show();
                txt_Amount.setText("");
            } else {
                Double tempBalance = Double.parseDouble(txt_Amount.getText()) - total;

                DecimalFormat dFormat = new DecimalFormat("#.##");
                String roundedNumber = dFormat.format(tempBalance);

                balance = Double.parseDouble(roundedNumber);

                lbl_Balance.setText("$ "+ balance);

                txt_Amount.getParent().requestFocus();  // to remove focus
            }
        }
    }

    @FXML
    void deleteRowByKey(KeyEvent event) {
        if(event.getCode() == KeyCode.DELETE){
            if(selectedRow == null){
                new Alert(Alert.AlertType.ERROR, "Please select the Product you want to remove").show();
            } else {
                cart.remove(selectedRow);
                getTotal();
                selectedRow = null;
                tbl_Cart.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    void resetAll() {
        total = 0;
        balance = 0;

        lbl_total.setText("LKR 0.00");
        lbl_Balance.setText("LKR 0.00");
        txt_Amount.setText("");

        txt_CustomerID.setText("");
        txt_Mobile.setText("");
        txt_FirstName.setText("");
        txt_LastName.setText("");
        txt_Address.setText("");

        ObservableList<Node> children = cardsGridPane.getChildren();

        for(Node child: children) {
            Label lbl_quantity = (Label) child.lookup("#lbl_Quantity");
            lbl_quantity.setText("0");
        }

        cart.clear();
        tbl_Cart.refresh();
    }

    @FXML
    void findExistCustomer() {
        try {
            cDTO = customerBO.findByContactNum(txt_Mobile.getText());

            if(cDTO!=null){
                txt_CustomerID.setText(cDTO.getCustomerID());
                txt_FirstName.setText(cDTO.getFirstName());
                txt_LastName.setText(cDTO.getLastName());
                txt_Address.setText(cDTO.getAddress());
            } else {
                txt_CustomerID.setText(customerBO.generateCustomerId());
//                cDTO = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddAllOnAction() {
        ObservableList<Node> allProductCards = cardsGridPane.getChildren();

        for (Node productCard : allProductCards) {
            Label lbl_quantity = (Label) productCard.lookup("#lbl_Quantity");
            int quantity = Integer.parseInt(lbl_quantity.getText());

            if (quantity != 0) {
                Label lbl_productID = (Label) productCard.lookup("#lbl_ProductID");
                String productID = lbl_productID.getText();

                try {
                    ProductDTO pDTO = productBO.findProduct(productID);

                    if (pDTO.getStock() < quantity) {
                        new Alert(Alert.AlertType.ERROR, "Insufficient Stocks").show();
                    } else {
                        boolean productAlreadyInCart = false;

                        for(CartTM cTM: cart){
                            int newQty;

                            if(productID.equals(cTM.getProductID())){
                                newQty = cTM.getQuantity() + quantity;

                                cTM.setQuantity(newQty);
                                cTM.setCost(pDTO.getUnitPrice()*newQty);

                                productAlreadyInCart = true;
                                break;
                            }
                        }

                        if(!productAlreadyInCart) {
                            cart.add(new CartTM(
                                    pDTO.getProductID(),
                                    pDTO.getDescription(),
                                    quantity,
                                    (pDTO.getUnitPrice() * quantity)
                            ));
                        }
                        totalQty += quantity;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (totalQty==0) {
            new Alert(Alert.AlertType.ERROR, "Please select a product first").show();
        } else {
            totalQty=0;

            try {
                lbl_OrderID.setText(orderBO.generateOrderId());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            tbl_Cart.setItems(cart);
            tbl_Cart.refresh();

            cardList.clear();
            displayAllCardsOnGrid();

            getTotal();
        }
    }

    @FXML
    boolean isOrderReady(){
        if (cart.isEmpty() || lbl_total.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please choose your order first!").show();
        } else if(
                txt_CustomerID.getText().isEmpty() ||
                        txt_FirstName.getText().isEmpty() ||
                        txt_LastName.getText().isEmpty() ||
                        txt_Address.getText().isEmpty() ||
                        txt_Mobile.getText().isEmpty() )
        {
            new Alert(Alert.AlertType.ERROR, "Please Fill Customer Details").show();
        } else if(txt_Amount.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Enter the Amount First").show();
        } else if (Double.parseDouble(txt_Amount.getText()) < total){
            txt_Amount.setText("");
            new Alert(Alert.AlertType.ERROR, "Insufficient Amount").show();
        } else {
            for(CartTM cartTM: cart){
                try {
                    if (productBO.getStock(cartTM.getProductID()) >= cartTM.getQuantity()){
                        orderInfoDTOList.add(new OrderInfoDTO(
                                lbl_OrderID.getText(),
                                cartTM.getProductID(),
                                cartTM.getQuantity() ));
                    } else {
                        orderInfoDTOList.clear();
                        new Alert(Alert.AlertType.ERROR,cartTM.getDescription()+" is out of stock!").show();
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    } // Done

    @FXML
    boolean isCustomerSet(){
        try {
            if (cDTO == null) {
                return customerBO.saveCustomer(new CustomerDTO(txt_CustomerID.getText(), txt_FirstName.getText(), txt_LastName.getText(), txt_Address.getText(), txt_Mobile.getText(), new Date()));
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    } // Done

    @FXML
    void btnPayOnAction() {
        if (isOrderReady()) {
            try {
                if(isCustomerSet()) {
                    OrderDTO orderDTO = new OrderDTO(
                            lbl_OrderID.getText(),
                            txt_CustomerID.getText(),
                            total,
                            Double.parseDouble(txt_Amount.getText()),
                            balance,
                            new Date()
                    );

                    if (orderBO.saveOrder(orderDTO, orderInfoDTOList)) {
                        orderInfoDTOList.clear();
                        resetAll();
                        new Alert(Alert.AlertType.INFORMATION, "Order Completed").show();
                    }
                }
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_ID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_Qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_Cost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        displayAllCardsOnGrid();

        try {
            lbl_OrderID.setText(orderBO.generateOrderId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        tbl_Cart.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> selectedRow = newValue));
    }
}