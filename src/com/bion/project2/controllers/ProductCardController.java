package com.bion.project2.controllers;

import com.bion.project2.dto.ProductDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCardController implements Initializable {

    @FXML
    private AnchorPane btn_Minus;

    @FXML
    private AnchorPane btn_Plus;

    @FXML
    private ImageView imgView;

    @FXML
    private Label lbl_UnitPrice;

    @FXML
    private Label lbl_ProductID;

    @FXML
    private Label lbl_Description;

    @FXML
    private Label lbl_Quantity;

    private int quantity = 0;

    public void setData(ProductDTO productDTO){
        lbl_ProductID.setText(productDTO.getProductID());
        lbl_Description.setText(productDTO.getDescription());
        lbl_UnitPrice.setText("LKR "+ productDTO.getUnitPrice());

        String imgPath = "File:" + productDTO.getImagePath();
        Image image = new Image(imgPath,230,125,false,true);
        imgView.setImage(image);
    }

    @FXML
    void changeQuantity(MouseEvent event) {
        if(event.getSource() == btn_Plus){
            quantity++;
            lbl_Quantity.setText(String.valueOf(quantity));
        } else if(event.getSource() == btn_Minus) {
            if(quantity>0){
                quantity--;
                lbl_Quantity.setText(String.valueOf(quantity));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
