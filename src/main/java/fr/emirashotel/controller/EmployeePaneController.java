package fr.emirashotel.controller;

import java.io.IOException;

import fr.emirashotel.DatabaseManager;
import fr.emirashotel.model.Customer;
import fr.emirashotel.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EmployeePaneController implements Initializable {

    @FXML
    private Button buttonemployee;

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
    private TextField employeeID;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField employeeMail;

    @FXML
    private TextField employeeAddress;

    @FXML
    private DatePicker employeeDOB;

    @FXML
    private DatePicker employeeContractDate;

    @FXML
    private TextField employeeRole;

    @FXML
    private Button employeeAddBtn;

    @FXML
    private Button employeeUpdateBtn;

    @FXML
    private Button employeeClearBtn;

    @FXML
    private Button employeeDeleteBtn;

    @FXML
    private TextField employeeSearch;

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, String> employeeCol_name;

    @FXML
    private TableColumn<Employee, Long> employeeCol_id;

    @FXML
    private TableColumn<Employee, String> employeeCol_mail;

    @FXML
    private TableColumn<Employee, String> employeeCol_role;

    @FXML
    private TableColumn<Employee, Date> employeeCol_contractDate;

    @FXML
    private TableColumn<Employee, String> employeeCol_address;

    @FXML
    private TableColumn<Employee, Date> employeeCol_dob;

    ObservableList<Employee> employees;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonEmployee.setStyle("-fx-background-color: #c1f3e1;");
        employeeDOB.setConverter(getCustomStringConverter());
        employeeContractDate.setConverter(getCustomStringConverter());
        employeeID.setTextFormatter(textFormatter());
        availableEmployeeShowListData();
        searchEmployee();
    }

    //region Employee Controller Functions
    public void availableEmployeeShowListData() {
        employees = FXCollections.observableArrayList();
        try {
            employees.addAll(DatabaseManager.getEmployees());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        employeeCol_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeCol_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeCol_mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        employeeCol_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        employeeCol_contractDate.setCellValueFactory(new PropertyValueFactory<>("contractStart"));
        employeeCol_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        employeeCol_dob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        employeeTableView.setItems(employees);
    }

    public void availableEmployeeSelect() {
        Employee employee = employeeTableView.getSelectionModel().getSelectedItem();
        int num = employeeTableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        employeeID.setText(String.valueOf(employee.getId()));
        employeeName.setText(employee.getName());
        employeeMail.setText(employee.getMail());
        employeeRole.setText(employee.getRole());
        employeeAddress.setText(employee.getAddress());
        employeeContractDate.setValue(LocalDate.parse(String.valueOf(employee.getContractStart())));
        employeeDOB.setValue(LocalDate.parse(String.valueOf(employee.getDateOfBirth())));
    }

    public void addEmployee() {
        try {
            long personId;
            if (employeeID.getText().isEmpty()) {
                personId = DatabaseManager.getNumberOfRows("person") + 1;
                while(DatabaseManager.checkID(personId)) {
                    personId++;
                }
            } else {
                personId = Long.parseLong(employeeID.getText());
                if (DatabaseManager.checkID(personId)) {
                    messageNotif("Error", "ID was already existed!");
                    return;
                }
            }
            String name = employeeName.getText();
            String email = employeeMail.getText();
            String address = employeeAddress.getText();
            LocalDate dob = employeeDOB.getValue();
            LocalDate contractDate = employeeContractDate.getValue();
            String role = employeeRole.getText();
            // Check if there are empty fields
            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || contractDate == null || dob == null || role.isEmpty()) {
                messageNotif("Error", "Please fill all blank fields!");
            } else {
                Employee employee = Employee.builder().id(personId).name(name).mail(email).dateOfBirth(Date.valueOf(dob)).address(address).contractStart(Date.valueOf(contractDate)).role(role).build();
                if (DatabaseManager.addEmployee(employee)) {
                    messageNotif("Information", "Successfully added!");
                    availableEmployeeShowListData();
                    clearContent();
                } else return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearContent() {
        employeeID.clear();
        employeeName.clear();
        employeeMail.clear();
        employeeAddress.clear();
        employeeDOB.setValue(null);
        employeeContractDate.setValue(null);
        employeeRole.clear();
    }

    public void updateEmployee() {
        try {
            String name = employeeName.getText();
            String email = employeeMail.getText();
            String address = employeeAddress.getText();
            LocalDate dob = employeeDOB.getValue();
            LocalDate contractDate = employeeContractDate.getValue();
            String role = employeeRole.getText();
            // Check if there are empty fields
            if (employeeID.getText().isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty() || contractDate == null || dob == null || role.isEmpty()) {
                messageNotif("Error", "Please fill all blank fields!");
            } else {
                messageNotif("Confirmation", "Are you sure want to UPDATE Employee " + employeeID.getText() + "?");
                Employee employee = Employee.builder().id(Long.valueOf(employeeID.getText())).name(name).mail(email).dateOfBirth(Date.valueOf(dob)).address(address).contractStart(Date.valueOf(contractDate)).role(role).build();
                if (DatabaseManager.updateEmployee(employee)) {
                    messageNotif("Information", "Successfully Updated!");
                    availableEmployeeShowListData();
                    clearContent();
                } else return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteEmployee() {
        try {
            // Check if there are empty fields
            if (employeeID.getText().isEmpty()) {
                messageNotif("Error", "Please fill blank ID field!");
            } else {
                if (!DatabaseManager.checkID(Long.valueOf(employeeID.getText()))) {
                    messageNotif("Error", "ID does not exist!");
                    return;
                }
                messageNotif("Confirmation", "Are you sure want to DELETE Employee " + employeeID.getText() + "?");
                Employee employee = Employee.builder().id(Long.valueOf(employeeID.getText())).build();
                if (DatabaseManager.deleteEmployee(employee)) {
                    messageNotif("Information", "Successfully Deleted!");
                    availableEmployeeShowListData();
                    clearContent();
                } else return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void searchEmployee() {
        FilteredList<Employee> filter = new FilteredList<>(employees, e->true);

        employeeSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateEmployeeData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateEmployeeData.getId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getMail().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getAddress().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getContractStart().toString().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getRole().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getDateOfBirth().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            ObservableList<Employee> employeeFilter = FXCollections.observableArrayList();
            employeeFilter.addAll(filter);
            employeeTableView.setItems(employeeFilter);
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

