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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import fr.emirashotel.DatabaseManager;
import fr.emirashotel.model.BookingRoom;
import fr.emirashotel.model.Customer;
import fr.emirashotel.model.Room;
import fr.emirashotel.model.RoomType;

public class HotelOrderPaneController implements Initializable {

    @FXML
    private Button buttonAddOrder;

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
    private TextField searchCustomer;

    @FXML
    private TextField searchRoom;

    @FXML
    private TextField searchBooking;

    @FXML
    private TableColumn<Customer, Date> customerDate;

    @FXML
    private TableColumn<Customer, String> customerMail;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableView<Customer> customerTab;

    @FXML
    private TableColumn<Room, Integer> roomNumber;

    @FXML
    private TableColumn<Room, Float> roomPrice;

    @FXML
    private TableView<Room> roomTab;

    @FXML
    private TableColumn<Room, RoomType> roomType;

    @FXML
    private TableColumn<BookingRoom, Integer> bookingCustomer;

    @FXML
    private TableColumn<BookingRoom, Date> bookingEnd;

    @FXML
    private TableColumn<BookingRoom, Date> bookingStart;

    @FXML
    private TableColumn<BookingRoom, Room> bookingRoom;

    @FXML
    private TableView<BookingRoom> bookingTab;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    ObservableList<Customer> customers;
    ObservableList<Room> rooms;
    ObservableList<BookingRoom> bookings;
    Customer customerSelect;
    Room roomSelect;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonHotelOrder.setStyle("-fx-background-color: #c1f3e1;");
        startDate.setConverter(getCustomStringConverter());
        endDate.setConverter(getCustomStringConverter());
        updateCustomer();
        updateRoom();
        updateBooking();
        customerTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                customerSelect = newSelection;
            }
        });
        roomTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                roomSelect = newSelection;
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
        if (customerSelect != null && roomSelect != null){
            if (startDate.getValue() != null && endDate.getValue() != null){
                Date start = Date.valueOf(startDate.getValue());
                Date end = Date.valueOf(endDate.getValue());
                if (startDate.getValue().compareTo(endDate.getValue()) < 0){
                    boolean verif = true;
                    long num=0;
                    for (BookingRoom booking :bookings){
                        if (!(start.compareTo(booking.getEnd())>0 ||end.compareTo(booking.getStart()) <0 ) && booking.getRoomNumber()==roomSelect.getNumber()){
                            verif=false;
                            num = booking.getCustomerID();
                        }
                    }
                    if (verif){
                        createOrder(customerSelect, roomSelect, start,end);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Your reservation for the room n° " +roomSelect.getNumber() + " has been confirmed");
                        alert.showAndWait();
                    }else{
                        error("This room is already reserve by the client n° "+num+"");
                    }
                }else{
                    error("The dates must be valid");
                }
            }else{
                error("Select the dates of the reservation");
            }
        }else{
            error("Select a Room and a Customer before Submit");
        }
    }

    public void createOrder(Customer customer,Room room,Date startDate,Date endDate) throws SQLException{
        long id = DatabaseManager.getNumberOfRows("bookingroom")+10;
        BookingRoom booking = new BookingRoom(id, customer, startDate, endDate, room);
        if (DatabaseManager.addBookingRoom(booking)){
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

    public void updateRoom(){
        rooms = FXCollections.observableArrayList();
        try {
            rooms.addAll(DatabaseManager.getRooms());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("type"));

        roomTab.setItems(rooms);
    }

    public void searchRoom() {
        FilteredList<Room> filter = new FilteredList<>(rooms, e->true);

        searchRoom.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateRoomData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (Integer.toString(predicateRoomData.getNumber()).contains(searchKey)) {
                    return true;
                } else if (Float.toString(predicateRoomData.getPrice()).contains(searchKey)) {
                    return true;
                } else if (predicateRoomData.getType().name().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            ObservableList<Room> roomFilter = FXCollections.observableArrayList();
            roomFilter.addAll(filter);
            roomTab.setItems(roomFilter);
        });
    }

    public void updateBooking(){
        bookings = FXCollections.observableArrayList();
        try {
            bookings.addAll(DatabaseManager.bookingRooms());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        bookingCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        bookingRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        bookingStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        bookingEnd.setCellValueFactory(new PropertyValueFactory<>("end"));

        bookingTab.setItems(bookings);
    }

    public void searchBooking() {
        FilteredList<BookingRoom> filter = new FilteredList<>(bookings, e->true);

        searchBooking.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateBookingData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(predicateBookingData.getCustomerID()).contains(searchKey)) {
                    return true;
                } else if(String.valueOf(predicateBookingData.getRoomNumber()).contains(searchKey)){
                    return true;
                } else {
                    return false;
                }
            });
            ObservableList<BookingRoom> bookingFilter = FXCollections.observableArrayList();
            bookingFilter.addAll(filter);
            bookingTab.setItems(bookingFilter);
        });
    }

    private StringConverter<LocalDate> getCustomStringConverter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                }
                return null;
            }
        };
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

