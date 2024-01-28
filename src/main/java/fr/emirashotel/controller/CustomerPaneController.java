package fr.emirashotel.controller;

import java.io.IOException;

import fr.emirashotel.DatabaseManager;
import fr.emirashotel.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.collections.ListChangeListener;

public class CustomerPaneController implements Initializable {

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
    private TextField customerID;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerMail;

    @FXML
    private TextField customerAddress;

    @FXML
    private DatePicker customerDOB;

    @FXML
    private DatePicker customerJoinDate;

    @FXML
    private TextField customerSearch;

    @FXML
    private Button customerAddBtn;

    @FXML
    private Button customerUpdateBtn;

    @FXML
    private Button customerClearBtn;

    @FXML
    private Button customerDeleteBtn;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> customerCol_name;

    @FXML
    private TableColumn<Customer, Long> customerCol_id;

    @FXML
    private TableColumn<Customer, String> customerCol_mail;

    @FXML
    private TableColumn<Customer, String> customerCol_address;

    @FXML
    private TableColumn<Customer, Date> customerCol_joinDate;

    @FXML
    private TableColumn<Customer, Date> customerCol_dob;

    ObservableList<Customer> customers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonCustomer.setStyle("-fx-background-color: #c1f3e1;");
        customerDOB.setConverter(getCustomStringConverter());
        customerJoinDate.setConverter(getCustomStringConverter());
        customerID.setTextFormatter(textFormatter());
        availableCustomerShowListData();
        searchCustomer();
    }

    //region Customer Controller Functions
    public void availableCustomerShowListData() {
        customers = FXCollections.observableArrayList();
        try {
            customers.addAll(DatabaseManager.getCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerCol_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerCol_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerCol_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        customerCol_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerCol_joinDate.setCellValueFactory(new PropertyValueFactory<>("joiningDate"));
        customerCol_dob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        customerTableView.setItems(customers);
    }

    public void availableCustomerSelect() {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        int num = customerTableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        customerID.setText(String.valueOf(customer.getId()));
        customerName.setText(customer.getName());
        customerMail.setText(customer.getMail());
        customerAddress.setText(customer.getAddress());
        customerDOB.setValue(LocalDate.parse(String.valueOf(customer.getDateOfBirth())));
        customerJoinDate.setValue(LocalDate.parse(String.valueOf(customer.getJoiningDate())));
    }

    public void customerAdd() {
        try {
            long personId;
            if (customerID.getText().isEmpty()) {
                personId = DatabaseManager.getNumberOfRows("person") + 1;
                while(DatabaseManager.checkID(personId)) {
                    personId++;
                }
            } else {
                personId = Long.parseLong(customerID.getText());
                if (DatabaseManager.checkID(personId)) {
                    messageNotif("Error", "ID was already existed!");
                    return;
                }
            }
            String name = customerName.getText();
            String email = customerMail.getText();
            String address = customerAddress.getText();
            LocalDate dob = customerDOB.getValue();
            LocalDate joiningDate = customerJoinDate.getValue();
            // Check if there are empty fields
            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || joiningDate == null || dob == null) {
                messageNotif("Error", "Please fill all blank fields!");
            } else {
                Customer customer = Customer.builder().id(personId).name(name).mail(email).dateOfBirth(Date.valueOf(dob)).address(address).joiningDate(Date.valueOf(joiningDate)).build();
                if (DatabaseManager.addCustomer(customer)) {
                    messageNotif("Information", "Successfully added!");
                    availableCustomerShowListData();
                    clearContent();
                } else return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearContent() {
        customerID.clear();
        customerName.clear();
        customerMail.clear();
        customerAddress.clear();
        customerDOB.setValue(null);
        customerJoinDate.setValue(null);
    }

    public void updateCustomer() {
        try {
            String name = customerName.getText();
            String email = customerMail.getText();
            String address = customerAddress.getText();
            LocalDate dob = customerDOB.getValue();
            LocalDate joiningDate = customerJoinDate.getValue();
            // Check if there are empty fields
            if (customerID.getText().isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty() || joiningDate == null || dob == null) {
                messageNotif("Error", "Please fill all blank fields!");
            } else {
                messageNotif("Confirmation", "Are you sure want to UPDATE Customer " + customerID.getText() + "?");
                Customer customerUpdate = Customer.builder().id(Long.valueOf(customerID.getText())).name(name).mail(email).dateOfBirth(Date.valueOf(dob)).address(address).joiningDate(Date.valueOf(joiningDate)).build();
                if (DatabaseManager.updateCustomer(customerUpdate)) {
                    messageNotif("Information", "Successfully Updated!");
                    availableCustomerShowListData();
                    clearContent();
                } else return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCustomer() {
        try {
            // Check if there are empty fields
            if (customerID.getText().isEmpty()) {
                messageNotif("Error", "Please fill blank ID field!");
            } else {
                if (!DatabaseManager.checkID(Long.valueOf(customerID.getText()))) {
                    messageNotif("Error", "ID does not exist!");
                    return;
                }
                messageNotif("Confirmation", "Are you sure want to DELETE Customer " + customerID.getText() + "?");
                Customer customerDelete = Customer.builder().id(Long.valueOf(customerID.getText())).build();
                if (DatabaseManager.deleteCustomer(customerDelete)) {
                    messageNotif("Information", "Successfully Deleted!");
                    availableCustomerShowListData();
                    clearContent();
                } else return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void searchCustomer() {
        FilteredList<Customer> filter = new FilteredList<>(customers, e->true);

        customerSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateCustomerData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateCustomerData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getMail().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getAddress().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomerData.getJoiningDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            ObservableList<Customer> customerFilter = FXCollections.observableArrayList();
            customerFilter.addAll(filter);
            customerTableView.setItems(customerFilter);
        });
    }
    //endregion

    //region Private Functions
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

    private TextFormatter<String> textFormatter() {
        // Create a UnaryOperator to filter the input
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change; // Accept the change
            }
            return null; // Reject the change
        };

        TextFormatter<String> textFormat = new TextFormatter<>(filter);
        return textFormat;
    }

    private void messageNotif(String type, String message) {
        Alert alert = new Alert(null);
        if (type.equals("Error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else if (type.equals("Confirmation")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        } else if (type.equals("Information")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(type + " Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //endregion

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

