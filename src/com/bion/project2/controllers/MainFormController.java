package com.bion.project2.controllers;

import com.bion.project2.dto.EmployeeDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    public ImageView imgView_Logo_Main;
    @FXML
    private Button btn_Customers;

    @FXML
    private Button btn_Dashboard;

    @FXML
    private Button btn_Inventory;

    @FXML
    private Button btn_OrderRecords;

    @FXML
    private Button btn_PlaceOrder;

    @FXML
    private Button btn_SignOut;

    @FXML
    private ImageView imgView_Employee;

    @FXML
    private Label lbl_EmployeeID;

    @FXML
    private Label lbl_EmployeeName;

    @FXML
    private AnchorPane tabChangeArea;

    @FXML
    void setEmployeeData(EmployeeDTO eDTO){
        String imgPath = "File:" + eDTO.getImagePath();
        Image image = new Image(imgPath,95,95, false, true);
        imgView_Employee.setImage(image);

        lbl_EmployeeName.setText(eDTO.getFirstName()+" "+eDTO.getLastName());

        lbl_EmployeeID.setText(eDTO.getEmployeeID());
    }

    @FXML
    void btn_SignOutOnAction() {
        try{
            Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure, You Want to Sign Out?").showAndWait();

            if(option.get().equals(ButtonType.OK)){
                btn_SignOut.getScene().getWindow().hide();

                Stage stage = new Stage();
                stage.setTitle("Cafe Shop Management System");

                Parent root = FXMLLoader.load(getClass().getResource("../views/loginForm.fxml"));
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadTab(String tabName){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/"+tabName+".fxml"));
            AnchorPane childPane = loader.load();

            tabChangeArea.getChildren().add(childPane);

            AnchorPane.setTopAnchor(childPane, 0.0);
            AnchorPane.setBottomAnchor(childPane, 0.0);
            AnchorPane.setLeftAnchor(childPane, 0.0);
            AnchorPane.setRightAnchor(childPane, 0.0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void navigateTabs(javafx.event.ActionEvent event) {
        if(event.getSource() == btn_Dashboard){
            tabChangeArea.getChildren().clear();
            loadTab("DashboardForm");
        }
        else if (event.getSource() == btn_Inventory) {
            tabChangeArea.getChildren().clear();
            loadTab("InventoryForm");
        }
        else if (event.getSource() == btn_Customers) {
            tabChangeArea.getChildren().clear();
            loadTab("CustomersForm");
        }
        else if (event.getSource() == btn_PlaceOrder) {
            tabChangeArea.getChildren().clear();
            loadTab("PlaceOrderForm");
        }
        else if (event.getSource() == btn_OrderRecords) {
            tabChangeArea.getChildren().clear();
            loadTab("OrderForm");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image logo_image = new Image("File:assets/images/logo_2.png", 195,105, true, true);
        imgView_Logo_Main.setImage(logo_image);
        imgView_Logo_Main.setTranslateX((195 - imgView_Logo_Main.getBoundsInParent().getWidth()) / 2); // Center horizontally
        imgView_Logo_Main.setTranslateY((105 - imgView_Logo_Main.getBoundsInParent().getHeight()) / 2); // Center vertically
        loadTab("DashboardForm");
    }
}
