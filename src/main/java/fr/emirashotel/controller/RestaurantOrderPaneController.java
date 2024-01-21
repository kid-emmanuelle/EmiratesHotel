package fr.emirashotel.controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fr.emirashotel.DatabaseManager;
import fr.emirashotel.model.Customer;
import fr.emirashotel.model.FoodDish;

public class RestaurantOrderPaneController implements Initializable {

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
    private TableColumn<Customer, Date> customerDate;

    @FXML
    private TableColumn<Customer, String> customerMail;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableView<Customer> customerTab;

    @FXML
    private TableColumn<FoodDish, String> foodName;

    @FXML
    private TableColumn<FoodDish, Float> foodPrice;

    @FXML
    private TableView<FoodDish> foodTab;

    @FXML
    private TableColumn<FoodDish, String> foodType;

    @FXML
    private TextField searchCustomer;

    @FXML
    private TextField searchFood;

    ObservableList<Customer> customers;
    Customer customerSelect;
    ObservableList<FoodDish> foods;
    FoodDish foodSelect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonRestaurantOrder.setStyle("-fx-background-color: #c1f3e1;");
        updateCustomer();
        updateFood();
        customerTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                customerSelect = newSelection;
            }
        });
        foodTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                foodSelect = newSelection;
            }
        });
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

    public void updateCustomer(){
        customers = FXCollections.observableArrayList();
        try {
            customers.addAll(DatabaseManager.getCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        customerDate.setCellValueFactory(new PropertyValueFactory<>("joiningDate"));

        customerTab.setItems(customers);
    }

    public void searchCustomer() {
        FilteredList<Customer> filter = new FilteredList<>(customers, e->true);

        searchCustomer.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateCustomerData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateCustomerData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            ObservableList<Customer> customerFilter = FXCollections.observableArrayList();
            customerFilter.addAll(filter);
            customerTab.setItems(customerFilter);
        });
    }

    public void updateFood(){
        foods = FXCollections.observableArrayList();
        try {
            foods.addAll(DatabaseManager.getDishes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        foodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        foodType.setCellValueFactory(new PropertyValueFactory<>("dishType"));
        foodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        foodTab.setItems(foods);
    }

    public void searchFood() {
        FilteredList<FoodDish> filter = new FilteredList<>(foods, e->true);

        searchFood.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateFoodData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateFoodData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(predicateFoodData.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else {
                    return false;
                }
            });
            ObservableList<FoodDish> foodFilter = FXCollections.observableArrayList();
            foodFilter.addAll(filter);
            foodTab.setItems(foodFilter);
        });
    }

}

