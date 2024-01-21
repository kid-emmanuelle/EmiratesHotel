package fr.emirashotel.controller;

import java.io.IOException;

import fr.emirashotel.DatabaseManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OverviewPaneController implements Initializable {

    @FXML
    private Button buttonCustomer;

    @FXML
    private Button buttonEmployee;

    @FXML
    private Button buttonHotelOrder;

    @FXML
    private Button buttonOverview;

    @FXML
    private Button buttonRestaurantOrder;

    @FXML
    private Button buttonSettings;

    @FXML
    private Label totalHotelOrders;

    @FXML
    private Label totalRestoOrders;

    @FXML
    private Label totalCustomers;

    @FXML
    private Label totalIncome;

    @FXML
    private BarChart<String, Number> incomeChart;

    @FXML
    private BarChart<String, Number> hotelOrderChart;

    @FXML
    private BarChart<String, Number> restoOrderChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonOverview.setStyle("-fx-background-color: #c1f3e1;");
        displayTotalHotelOrders();
        displayTotalRestoOrders();
        displayTotalCustomers();
        displayTotalIncome();
        setDataIncome();
        setHotelOrderData();
        setRestoOrderData();
    }

    private void displayTotalHotelOrders() {
        try {
            totalHotelOrders.setText(String.valueOf(DatabaseManager.getNumberOfRows("bookingroom")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayTotalRestoOrders() {
        try {
            totalRestoOrders.setText(String.valueOf(DatabaseManager.getNumberOfRows("bookingrestaurant")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayTotalCustomers() {
        try {
            totalCustomers.setText(String.valueOf(DatabaseManager.getNumberOfRows("customer")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayTotalIncome() {
        int hotelOrders = Integer.parseInt(totalHotelOrders.getText());
        int restoOrders = Integer.parseInt(totalRestoOrders.getText());
        totalIncome.setText(String.valueOf((hotelOrders + restoOrders) * 55) + " $");
    }

    private void setDataIncome() {
        incomeChart.setBarGap(5);
        incomeChart.setCategoryGap(35);
        // Creating a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Income per Day");

        // Adding data points to the series
        series.getData().add(new XYChart.Data<>("Monday", 100));
        series.getData().add(new XYChart.Data<>("Tuesday", 150));
        series.getData().add(new XYChart.Data<>("Wednesday", 200));
        series.getData().add(new XYChart.Data<>("Thursday", 180));
        series.getData().add(new XYChart.Data<>("Friday", 220));

        // Adding the series to the existing chart
        incomeChart.getData().add(series);
    }

    private void setHotelOrderData() {
        hotelOrderChart.setBarGap(5);
        hotelOrderChart.setCategoryGap(35);
        // Creating a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Hotel Orders per Day");

        // Adding data points to the series
        series.getData().add(new XYChart.Data<>("Monday", 30));
        series.getData().add(new XYChart.Data<>("Tuesday", 45));
        series.getData().add(new XYChart.Data<>("Wednesday", 20));
        series.getData().add(new XYChart.Data<>("Thursday", 60));
        series.getData().add(new XYChart.Data<>("Friday", 50));

        // Apply the style to each Data object in the series
        for (XYChart.Data<String, Number> data : series.getData()) {
            Platform.runLater(() -> {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-bar-fill: #4bd3c8;");
                }
            });
        }

        // Adding the series to the chart
        hotelOrderChart.getData().add(series);
    }

    private void setRestoOrderData() {
        restoOrderChart.setBarGap(5);
        restoOrderChart.setCategoryGap(35);

        // Creating a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Restaurant Orders per Day");

        // Adding data points to the series
        series.getData().add(new XYChart.Data<>("Monday", 10));
        series.getData().add(new XYChart.Data<>("Tuesday", 17));
        series.getData().add(new XYChart.Data<>("Wednesday", 27));
        series.getData().add(new XYChart.Data<>("Thursday", 19));
        series.getData().add(new XYChart.Data<>("Friday", 32));

        // Apply the style to each Data object in the series
        for (XYChart.Data<String, Number> data : series.getData()) {
            Platform.runLater(() -> {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-bar-fill: #be97ef;");
                }
            });
        }

        // Adding the series to the chart
        restoOrderChart.getData().add(series);
    }

    @FXML
    void redirect(MouseEvent event) {
        Node source = (Node) event.getSource();
        // Récupérer l'ID de l'élément cliqué
        String id = source.getId();
        String name = "";
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        switch (id){
            case "buttonCustomer":
                name = "CustomerPane.fxml";
                break;  
            case "buttonEmployee":
                name = "EmployeePane.fxml";
                break;
            case "buttonHotelOrder":
                name = "HotelOrderPane.fxml";
                break;
            case "buttonOverview":
                name = "OverviewPane.fxml";
                break;
            case "buttonRestaurantOrder":
                name = "RestaurantOrderPane.fxml";
                break;
            case "buttonSettings":
                name = "SettingsPane.fxml";
                break;
        }
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+name));
                Parent newroot=loader.load();
                Scene newscene = new Scene(newroot);
                window.setScene(newscene);
                window.setResizable(true);
                window.show();
            } catch ( IOException e) {
                e.printStackTrace();
            }
    }

}

