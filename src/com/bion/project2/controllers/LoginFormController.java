package com.bion.project2.controllers;

import com.bion.project2.bo.BOFactory;
import com.bion.project2.bo.custom.EmployeeBO;
import com.bion.project2.dto.EmployeeDTO;
import com.bion.project2.enums.BOType;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFormController implements Initializable {
    @FXML
    public ImageView imgView_Logo_Login;
    @FXML
    public ImageView imgView_Login;

    @FXML
    private Button btnAlreadyHave;

    @FXML
    private Button btnBack_Fgt;

    @FXML
    private Button btnBack_NPw;

    @FXML
    private Button btnBack_SUp;

    @FXML
    private Button btnCreateAcc;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnNext_Fgt;

    @FXML
    private Button btnNext_SUp;

    @FXML
    private ComboBox<String> cmbQuestion_FPw;

    @FXML
    private ComboBox<String> cmbQuestion_SUp;

    @FXML
    private ImageView imgView_Employee;

    @FXML
    private Hyperlink linkForgot;

    @FXML
    private StackPane loginForm;

    @FXML
    private AnchorPane pane_FPw;

    @FXML
    private AnchorPane pane_Image;

    @FXML
    private AnchorPane pane_Login;

    @FXML
    private AnchorPane pane_NPw;

    @FXML
    private AnchorPane pane_SUp1;

    @FXML
    private AnchorPane pane_SUp2;

    @FXML
    private TextField txtAddress_SUp;

    @FXML
    private TextField txtAnswer_Fgt;

    @FXML
    private TextField txtAnswer_SUp;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtFirstName_SUp;

    @FXML
    private TextField txtLastName_SUp;

    @FXML
    private TextField txtMobile_SUp;

    @FXML
    private TextField txtNIC_SUp;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtPassword_Log;

    @FXML
    private PasswordField txtPassword_SUp;

    @FXML
    private TextField txtUsername_Fgt;

    @FXML
    private TextField txtUsername_Log;

    @FXML
    private TextField txtUsername_SUp;

    private Image image;
    private Image logo_image;

    private String imagePath;

    private final EmployeeBO employeeBO = BOFactory.getInstance().getBO(BOType.EMPLOYEE);

    private final String[] questionList = {
            "What is your favorite Color?",
            "What is your favourite food?",
            "What is your birth day"
    };

    @FXML
    void importImage() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image file", "*png", "*jpg"));

        File file = openFile.showOpenDialog(loginForm.getScene().getWindow());

        if(file != null) {
            imagePath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 137,137, false, true);
            imgView_Employee.setImage(image);
        }
    } // Done

    @FXML
    boolean isValidUsername(String username){
        // Allows only alphanumeric characters and underscores
        String pattern = "^[a-zA-Z0-9_]+$";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(username);

        return matcher.matches();
    }

    @FXML
    boolean isValidPassword(String password){
        // Minimum length requirement
        if (password.length() < 8) {
            new Alert(Alert.AlertType.ERROR,"Password must be at least 8 characters long.").show();
            return false;
        }

        // At least one uppercase letter, one lowercase letter, and one digit
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$]).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            new Alert(Alert.AlertType.ERROR,"Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (!@#$)").show();
            return false;
        }

        // No spaces allowed
        if (password.contains(" ")) {
            new Alert(Alert.AlertType.ERROR,"Password cannot contain spaces.").show();
            return false;
        }
        return true;
    }

    @FXML
    boolean isValidNIC(String nicNumber){
        // Sri Lankan NIC format
        String regex = "^[0-9]{9}[vVxX]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nicNumber);

        return matcher.matches();
    }

    @FXML
    boolean isValidMobile(String mobileNum){
        String pattern1 = "\\+947\\d{8}";
        String pattern2 = "07\\d{8}";

        Pattern regex1 = Pattern.compile(pattern1);
        Pattern regex2 = Pattern.compile(pattern2);

        Matcher matcher1 = regex1.matcher(mobileNum);
        Matcher matcher2 = regex2.matcher(mobileNum);

        return matcher1.matches() || matcher2.matches();
    }

    @FXML
    boolean isValidNameInput(String name){
        String pattern = "^[A-Za-z\\s]+$";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(name);

        return matcher.find();
    }

    @FXML
    void clearAllFields(){
        imgView_Employee.setImage(null);
        txtFirstName_SUp.setText("");
        txtLastName_SUp.setText("");
        txtAddress_SUp.setText("");
        txtMobile_SUp.setText("");
        txtUsername_SUp.setText("");
        txtPassword_SUp.setText("");
        txtNIC_SUp.setText("");
        cmbQuestion_SUp.getSelectionModel().clearSelection();
        txtAnswer_SUp.setText("");

        txtUsername_Log.setText("");
        txtPassword_Log.setText("");

        txtUsername_Fgt.setText("");
        cmbQuestion_FPw.getSelectionModel().clearSelection();
        txtAnswer_Fgt.setText("");

        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }

    @FXML
    void btnSignUpOnAction() {
        if(txtUsername_SUp.getText().isEmpty()||
                txtPassword_SUp.getText().isEmpty()||
                txtNIC_SUp.getText().isEmpty()||
                cmbQuestion_SUp.getSelectionModel().getSelectedItem() == null||
                txtAddress_SUp.getText().isEmpty())
        {
            new Alert(Alert.AlertType.ERROR,"Please fill all blank fields").show();
        }

        else {
            try {
                if(employeeBO.isUserExist(txtUsername_SUp.getText())){
                    new Alert(Alert.AlertType.ERROR,txtUsername_SUp.getText()+" is already taken ").show();
                } else if(!isValidUsername(txtUsername_SUp.getText())) {
                    new Alert(Alert.AlertType.ERROR,"InValid Username").show();
                } else if(!isValidNIC(txtNIC_SUp.getText())) {
                    new Alert(Alert.AlertType.ERROR,"Invalid NIC number").show();
                } else if(isValidPassword(txtPassword_SUp.getText())) {
                    EmployeeDTO newEmpDTO = new EmployeeDTO(
                            employeeBO.generateEmployeeID(),
                            txtFirstName_SUp.getText(),
                            txtLastName_SUp.getText(),
                            txtAddress_SUp.getText(),
                            txtMobile_SUp.getText(),
                            imagePath,
                            txtUsername_SUp.getText(),
                            txtPassword_SUp.getText(),
                            txtNIC_SUp.getText(),
                            cmbQuestion_SUp.getValue(),
                            txtAnswer_SUp.getText(),
                            new Date());

                    if(employeeBO.saveEmployee(newEmpDTO)){
                        new Alert(Alert.AlertType.INFORMATION, "Successfully Registered Account!").show();

                        clearAllFields();

                        pane_SUp2.setVisible(false);
                        pane_SUp1.setVisible(true);

                        TranslateTransition slider = new TranslateTransition();

                        slider.setNode(pane_Image);
                        slider.setToX(0);
                        slider.setDuration(Duration.seconds(.5));
                        slider.setOnFinished((ActionEvent e) -> {
                            btnAlreadyHave.setVisible(false);
                            btnCreateAcc.setVisible(true);
                        });

                        slider.play();
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Registration Failed").show();

                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    } // Done

    @FXML
    void btnLoginOnAction() {
        if(txtUsername_Log.getText().isEmpty() || txtPassword_Log.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all blank fields").show();
        } else {
            try {
                if(!employeeBO.isUserExist(txtUsername_Log.getText())){
                    new Alert(Alert.AlertType.ERROR, "User Does not Exist").show();

                } else if(employeeBO.isValidLogin(txtUsername_Log.getText(),txtPassword_Log.getText())){

                    Stage stage = new Stage();
                    stage.setTitle("Coffee Shop Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    stage.setResizable(true);
                    stage.centerOnScreen();

                    URL resourceURL = getClass().getResource("../views/MainForm.fxml");

                    if(resourceURL!=null){
                        FXMLLoader loader = new FXMLLoader(resourceURL);

                        Parent root = loader.load();

                        MainFormController mCtrl = loader.getController();

                        mCtrl.setEmployeeData(employeeBO.getEmployee(txtUsername_Log.getText()));

                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                        clearAllFields();
                        btnLogin.getScene().getWindow().hide();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR,"Incorrect password").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // Done

    @FXML
    void btnChangePwOnAction(){
        if(txtNewPassword.getText().isEmpty()||txtConfirmPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please fill all blank fields").show();
        } else if(!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Passwords are not matching!").show();
        } else {
            try {
                if(employeeBO.isPasswordChanged(txtUsername_Fgt.getText(),txtNewPassword.getText())){
                    new Alert(Alert.AlertType.INFORMATION,"Successfully changed password!").show();

                    clearAllFields();
                    pane_Login.setVisible(true);
                    pane_FPw.setVisible(false);
                    pane_NPw.setVisible(false);
                }
            } catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.INFORMATION,"Something Wrong!").show();
            }
        }
    } // Done

    @FXML
    void validateForgotUser() {
        if(txtUsername_Fgt.getText().isEmpty() || cmbQuestion_FPw.getSelectionModel().getSelectedItem() == null || txtAnswer_Fgt.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please fill all blank fields").showAndWait();
        } else {
            try {
                if(employeeBO.isSecretAnswerCorrect(txtUsername_Fgt.getText(), cmbQuestion_FPw.getValue(), txtAnswer_Fgt.getText())){
                    pane_FPw.setVisible(false);
                    pane_NPw.setVisible(true);
                } else {
                    new Alert(Alert.AlertType.ERROR,"Incorrect Information").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // Done

    public void navigate(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();
        slider.setNode(pane_Image);

        if(event.getSource() == btnCreateAcc ){
            slider.setToX(350);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e)->{
                btnAlreadyHave.setVisible(true);
                btnCreateAcc.setVisible(false);
            });
            slider.play();
        }

        else if (event.getSource() == btnAlreadyHave) {
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e)->{
                btnAlreadyHave.setVisible(false);
                btnCreateAcc.setVisible(true);
            });
            slider.play();
        }

        else if (event.getSource() == linkForgot) {
            pane_Login.setVisible(false);
            pane_FPw.setVisible(true);
        }

        else if (event.getSource() == btnNext_Fgt) {
            validateForgotUser();
        }

        else if (event.getSource() == btnBack_Fgt) {
            pane_Login.setVisible(true);
            pane_FPw.setVisible(false);

            txtUsername_Fgt.setText("");
            cmbQuestion_FPw.getSelectionModel().clearSelection();
            txtAnswer_Fgt.setText("");
        }

        else if (event.getSource() == btnBack_NPw) {
            pane_FPw.setVisible(true);
            pane_NPw.setVisible(false);
            txtNewPassword.setText("");
            txtConfirmPassword.setText("");
        }

        else if (event.getSource() == btnNext_SUp) {
            if(imgView_Employee.getImage() == null ||txtFirstName_SUp.getText().isEmpty() ||
                    txtLastName_SUp.getText().isEmpty() ||
                    txtAddress_SUp.getText().isEmpty() ||
                    txtMobile_SUp.getText().isEmpty())
            {
                new Alert(Alert.AlertType.ERROR,"Please fill blank fields").show();
            } else if(!isValidNameInput(txtFirstName_SUp.getText())){
                new Alert(Alert.AlertType.ERROR,"First name is not in valid format. Please Enter in correct format").show();
            } else if(!isValidNameInput(txtLastName_SUp.getText())){
                new Alert(Alert.AlertType.ERROR,"Last name is not in valid format. Please Enter in correct format").show();
            } else if(!isValidMobile(txtMobile_SUp.getText())){
                new Alert(Alert.AlertType.ERROR,"Invalid Mobile Number. Please Enter in correct format").show();
            } else {
                pane_SUp2.setVisible(true);
                pane_SUp1.setVisible(false);
            }
        }

        else if (event.getSource() == btnBack_SUp) {
            pane_SUp1.setVisible(true);
            pane_SUp2.setVisible(false);

            txtUsername_SUp.setText("");
            txtPassword_SUp.setText("");
            txtNIC_SUp.setText("");
            cmbQuestion_SUp.getSelectionModel().clearSelection();
            txtAnswer_SUp.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image logo_image = new Image("File:assets/images/logo_2.png",330,230, true, true);
        imgView_Logo_Login.setImage(logo_image);
        imgView_Logo_Login.setTranslateX((330 - imgView_Logo_Login.getBoundsInParent().getWidth()) / 2);
        imgView_Logo_Login.setTranslateY((230 - imgView_Logo_Login.getBoundsInParent().getHeight()) / 2);

        Image wallpaper = new Image("File:assets/images/bg_image1.jpg",350,500, true, true);
        imgView_Login.setImage(wallpaper);

        ObservableList<String> list = FXCollections.observableArrayList(questionList);

        cmbQuestion_FPw.setItems(list);
        cmbQuestion_SUp.setItems(list);
    } // Done
}
