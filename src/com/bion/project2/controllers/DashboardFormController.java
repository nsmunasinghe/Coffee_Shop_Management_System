package com.bion.project2.controllers;

import com.bion.project2.bo.BOFactory;
import com.bion.project2.bo.custom.CustomerBO;
import com.bion.project2.bo.custom.OrderBO;
import com.bion.project2.dto.CustomerDTO;
import com.bion.project2.dto.OrderDTO;
import com.bion.project2.enums.BOType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardFormController implements Initializable {

    @FXML
    private BarChart<String,Integer> barChart_Customers;

    @FXML
    private Label lbl_Clock;

    @FXML
    private Label lbl_OrderCount;

    @FXML
    private Label lbl_SoldProductsCount;

    @FXML
    private Label lbl_TodayIncome;

    @FXML
    private Label lbl_TotalIncome;

    @FXML
    private LineChart<String,Double> lineChart_Income;

    @FXML
    private PieChart pieChart_SoldProducts;

    private final OrderBO orderBO = BOFactory.getInstance().getBO(BOType.ORDER);
    private final CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER);

    @FXML
    void animateTimeline(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                event -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy dd HH:mm:ss");
                    String formattedTime = dateFormat.format(new Date());
                    lbl_Clock.setText(formattedTime);
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    @FXML
    void displayOrderCount() {
        try {
            lbl_OrderCount.setText(String.valueOf(orderBO.getAllOrders().size()));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayTodayIncome() {
        try {
            lbl_TodayIncome.setText("LKR " + orderBO.getTodayIncome());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayTotalIncome() {
        try {
            lbl_TotalIncome.setText("LKR " + orderBO.getTotalIncome());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displaySoldCount() {
        try {
            lbl_SoldProductsCount.setText(String.valueOf(orderBO.getSoldCount()));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayIncomeChart() {
        XYChart.Series<String,Double> series = new XYChart.Series<>();

        try{
            Map<Date,Double> totalByDate = new HashMap<>();

            for(OrderDTO orderDTO: orderBO.getAllOrders()){
                totalByDate.merge(orderDTO.getDate(), orderDTO.getTotal(), Double::sum);
            }

            for (Map.Entry<Date, Double> entry: totalByDate.entrySet()){
                LocalDate fullDate= entry.getKey().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String formattedDate = fullDate.format(DateTimeFormatter.ofPattern("MMM dd"));

                series.getData().add(new XYChart.Data<>(formattedDate, entry.getValue()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        lineChart_Income.getData().add(series);
    }

    @FXML
    void displayCustomersChart() {
        try{
            XYChart.Series<String,Integer> series = new XYChart.Series<>();
            Map<Date,Integer> customerCountByDate  = new HashMap<>();

            List<CustomerDTO> allCustomers = customerBO.findAllCustomers();

            for(CustomerDTO cDTO: allCustomers){
                Date registerDate = cDTO.getDate();
                customerCountByDate.merge(registerDate, 1, Integer::sum);
            }

            for (Map.Entry<Date, Integer> entry: customerCountByDate.entrySet()){
                LocalDate fullDate= entry.getKey().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String formattedDate = fullDate.format(DateTimeFormatter.ofPattern("MMM dd"));
                series.getData().add(new XYChart.Data<>(formattedDate, entry.getValue()));
            }

            barChart_Customers.getData().add(series);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void displaySoldProductChart(){
        ObservableList<PieChart.Data> soldProducts = FXCollections.observableArrayList();

        try{
            for (Map.Entry<String, Integer> entry: orderBO.getSoldCountWithDescription().entrySet()){
                soldProducts.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        pieChart_SoldProducts.setData(soldProducts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animateTimeline();

        displayOrderCount();
        displayTodayIncome();
        displayTotalIncome();
        displaySoldCount();

        displayIncomeChart();
        displayCustomersChart();
        displaySoldProductChart();
    }
}