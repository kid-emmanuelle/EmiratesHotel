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
import javafx.scene.control.Alert;
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
import fr.emirashotel.model.BookingRestaurant;
import fr.emirashotel.model.BookingRoom;
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
    private TableColumn<BookingRestaurant, Integer> bookingCustomer;

    @FXML
    private TableColumn<BookingRestaurant, FoodDish> bookingDish;

    @FXML
    private TableColumn<BookingRestaurant, Float> bookingPrice;

    @FXML
    private TableColumn<BookingRestaurant, Integer> bookingQuantity;

    @FXML
    private TableView<BookingRestaurant> bookingTab;

    @FXML
    private TextField searchCustomer;

    @FXML
    private TextField searchFood;

    @FXML
    private TextField searchBooking;

    @FXML
    private TextField quantity;

    @FXML 
    private Button buttonAddOrder;

    ObservableList<Customer> customers;
    Customer customerSelect;
    ObservableList<FoodDish> foods;
    FoodDish foodSelect;
    ObservableList<BookingRestaurant> bookings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonRestaurantOrder.setStyle("-fx-background-color: #c1f3e1;");
        updateCustomer();
        updateFood();
        updateBooking();
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

    @FXML
    void addOrder(MouseEvent event) throws SQLException {
        if (customerSelect != null && foodSelect != null){
            Integer quant;
            try {
                quant = Integer.parseInt(quantity.getText());
            } catch (NumberFormatException e) {
                error("Type a number");
                return;
            }
            if (quantity.getText() != null && quant<20){
                createOrder(customerSelect, foodSelect, quant);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Your command for "+ quant+" " +foodSelect.getDishName() + " has been confirmed");
                alert.showAndWait();
            }else{
                error("Type a quantity between 1 and 20");
            }
        }else{
            error("Select a customer and a dish before submit");
        }
    }

    
    private void createOrder(Customer customer, FoodDish food, Integer quantity) throws SQLException {
        long id = DatabaseManager.getNumberOfRows("bookingrestaurant")+10;
        BookingRestaurant booking = new BookingRestaurant(id, customer, food, quantity);
        if (DatabaseManager.addBookingRestaurant(booking)){
            updateBooking();
        }else{
            error("Erreur Interne");
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
        foodName.setCellValueFactory(new PropertyValueFactory<>("dishName"));
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

    public void updateBooking(){
        bookings = FXCollections.observableArrayList();
        try {
            bookings.addAll(DatabaseManager.bookingDishes());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        bookingCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        bookingDish.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        bookingQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookingPrice.setCellValueFactory(new PropertyValueFactory<>("foodPrice"));

        bookingTab.setItems(bookings);
    }

    public void searchBooking() {
        FilteredList<BookingRestaurant> filter = new FilteredList<>(bookings, e->true);

        searchBooking.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateBookingData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateBookingData.getFoodName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if(String.valueOf(predicateBookingData.getCustomerID()).contains(searchKey)){
                    return true;
                } else {
                    return false;
                }
            });
            ObservableList<BookingRestaurant> bookingFilter = FXCollections.observableArrayList();
            bookingFilter.addAll(filter);
            bookingTab.setItems(bookingFilter);
        });
    }

    public void error(String text){
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}

