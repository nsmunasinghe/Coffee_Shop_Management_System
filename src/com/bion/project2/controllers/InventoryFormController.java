package com.bion.project2.controllers;

import com.bion.project2.bo.BOFactory;
import com.bion.project2.bo.custom.ProductBO;
import com.bion.project2.dto.ProductDTO;
import com.bion.project2.enums.BOType;
import com.bion.project2.views.tableModels.ProductTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class InventoryFormController implements Initializable {

    @FXML
    private AnchorPane inventoryPane;

    @FXML
    private ComboBox<String> cmb_Type;

    @FXML
    private TableView<ProductTM> tbl_Product;

    @FXML
    private TableColumn<ProductTM, String> col_Date;

    @FXML
    private TableColumn<ProductTM, String> col_ProductID;

    @FXML
    private TableColumn<ProductTM, String> col_Description;


    @FXML
    private TableColumn<ProductTM, String> col_Type;

    @FXML
    private TableColumn<ProductTM, String> col_UnitPrice;


    @FXML
    private TableColumn<ProductTM, String> col_Stock;

    @FXML
    private ImageView imgView_Product;

    @FXML
    private TextField txt_ProductID;

    @FXML
    private TextField txt_Description;

    @FXML
    private TextField txt_Stock;

    @FXML
    private TextField txt_UnitPrice;

    private Image image;

    String imagePath;

    private final ProductBO productBO = BOFactory.getInstance().getBO(BOType.PRODUCT);

    private final String[] types = {"Meals", "Drinks"};

    public void loadTypeList() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, types); // instead of for-loop
        ObservableList<String> typeList = FXCollections.observableArrayList(list);
        cmb_Type.setItems(typeList);
    }

    private void setFieldData(ProductTM newValue) {
        txt_ProductID.setText(newValue.getProductID());
        txt_Description.setText(newValue.getDescription());
        cmb_Type.setValue(newValue.getType());
        txt_UnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txt_Stock.setText(String.valueOf(newValue.getStock()));

        try {
            imagePath = "File:" + productBO.findProduct(newValue.getProductID()).getImagePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        image = new Image(imagePath, 140, 127, false, true);

        imgView_Product.setImage(image);
    } // Done

    public void loadTableData() {
        ObservableList<ProductTM> productTMList = FXCollections.observableArrayList();

        try{
            for (ProductDTO pDTO: productBO.findAllProducts()){
                productTMList.add(new ProductTM(
                        pDTO.getProductID(),
                        pDTO.getDescription(),
                        pDTO.getType(),
                        pDTO.getUnitPrice(),
                        pDTO.getStock(),
                        pDTO.getDate()
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        tbl_Product.setItems(productTMList);
    } // Done

    @FXML
    void importImage() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image file", "*png", "*jpg"));

        File file = openFile.showOpenDialog(inventoryPane.getScene().getWindow());

        if(file != null) {
            imagePath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 140,127, false, true);
            imgView_Product.setImage(image);
        }
    } // Done

    @FXML
    void btnAddOnAction() {
        if(txt_Description.getText().isEmpty()
                || cmb_Type.getSelectionModel().getSelectedItem() == null
                || txt_UnitPrice.getText().isEmpty()
                || txt_Stock.getText().isEmpty()
                || imagePath == null)
        {
            new Alert(Alert.AlertType.ERROR, "Please fill all blank fields!").show();
        } else {
            try{
                if(productBO.isProductExist(txt_ProductID.getText())){
                    new Alert(Alert.AlertType.ERROR, txt_ProductID.getText() + " is already taken").show();
                } else {
                    productBO.saveProduct(new ProductDTO(
                            productBO.generateProductId(),
                            txt_Description.getText(),
                            cmb_Type.getSelectionModel().getSelectedItem(),
                            Double.parseDouble(txt_UnitPrice.getText()),
                            Integer.parseInt(txt_Stock.getText()),
                            imagePath,
                            new Date()
                    ));

                    new Alert(Alert.AlertType.INFORMATION, "Successfully Added").show();

                    loadTableData();

                    btnClearOnAction();
                }
            } catch (ClassNotFoundException | SQLException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    } // Done

    @FXML
    void btnUpdateOnAction() {
        if(txt_ProductID.getText().isEmpty()
                || txt_Description.getText().isEmpty()
                || cmb_Type.getSelectionModel().getSelectedItem() == null
                || txt_UnitPrice.getText().isEmpty()
                || txt_Stock.getText().isEmpty()
                || imgView_Product.getImage() == null)
        {
            new Alert(Alert.AlertType.WARNING, "Please fill all blank fields!").show();
        } else {
            try{
                productBO.updateProduct(new ProductDTO(
                        txt_ProductID.getText(),
                        txt_Description.getText(),
                        cmb_Type.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(txt_UnitPrice.getText()),
                        Integer.parseInt(txt_Stock.getText()),
                        imagePath,
                        new Date()
                ));

                new Alert(Alert.AlertType.INFORMATION, "Successfully Updated!").show();

                loadTableData();
                btnClearOnAction();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // Done

    @FXML
    void btnClearOnAction() {
        tbl_Product.getSelectionModel().clearSelection();
        txt_ProductID.setText("");
        txt_Description.setText("");
        cmb_Type.getSelectionModel().clearSelection();
        txt_UnitPrice.setText("");
        txt_Stock.setText("");
        imgView_Product.setImage(null);
        imagePath = null;
    }

    @FXML
    void btnDeleteOnAction() {
        if(txt_ProductID == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a product first!").show();
        } else {
            try{
                Optional<ButtonType> option = new Alert(
                        Alert.AlertType.WARNING,
                        "Are you sure you want to Delete Product ID:")
                        .showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    productBO.deleteProduct(txt_ProductID.getText());

                    new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted!").show();
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Cancelled").show();
                }

                loadTableData();
                btnClearOnAction();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    } // Done

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        col_Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        col_Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTypeList();

        loadTableData();

        tbl_Product.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setFieldData(newValue);
                    }
                }));
    }
}