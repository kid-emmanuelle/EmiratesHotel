package fr.emirashotel;

import fr.emirashotel.model.Customer;
import fr.emirashotel.model.Employee;
import fr.emirashotel.model.Order;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static Connection connection;

    private static final String url = "jdbc:mysql://localhost/emiras";
    private static final String user = "root";

    private static final String password = "";


    public static void create() throws SQLException, ClassNotFoundException{
        if(connection != null) return;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, password);
    }

    public static void destroy() throws SQLException {
        if(connection == null) return;
        connection.close();
    }

    public static ArrayList<Employee> getEmployees() throws SQLException {
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM employee JOIN person USING(personID)";
        ResultSet resultset = statement.executeQuery(requete);
        ArrayList<Employee> employees = new ArrayList<>();

        while (resultset.next()){
            employees.add(
                    Employee.builder()
                            .id(resultset.getLong("personID"))
                            .name(resultset.getString("name"))
                            .mail(resultset.getString("mail"))
                            .dateOfBirth(resultset.getDate("dateOfBirth"))
                            .address(resultset.getString("address"))
                            .contractStart(resultset.getDate("contractStart"))
                            .role(resultset.getString("role"))
                            .build()
            );
        }
        resultset.close();
        statement.close();
        return employees;
    }

    public static ArrayList<Customer> getCustomers() throws SQLException {
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM customer JOIN person USING(personID)";
        ResultSet resultset = statement.executeQuery(requete);
        ArrayList<Customer> customers = new ArrayList<>();

        while (resultset.next()){
            customers.add(
                    Customer.builder()
                            .id(resultset.getLong("personID"))
                            .name(resultset.getString("name"))
                            .mail(resultset.getString("mail"))
                            .dateOfBirth(resultset.getDate("dateOfBirth"))
                            .address(resultset.getString("address"))
                            .joiningDate(resultset.getDate("joiningDate"))
                            .build()
            );
        }
        resultset.close();
        statement.close();
        return customers;
    }

    public static ArrayList<Order> getOrders(Customer customer) throws SQLException{
        String requete = "SELECT * FROM command WHERE customer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(requete);
        preparedStatement.setLong(1, customer.getId());

        ResultSet resultset = preparedStatement.executeQuery(requete);
        ArrayList<Order> orders = new ArrayList<>();

        while (resultset.next()){
            orders.add(Order.builder()
                    .id(resultset.getLong("OrderID"))
                    .payment(resultset.getDate("payment"))
                    .customer(customer).build());
        }

        resultset.close();
        preparedStatement.close();
        return orders;
    }

    public static ArrayList<Order> get(Customer customer) throws SQLException{
        String requete = "SELECT * FROM command WHERE customer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(requete);
        preparedStatement.setLong(1, customer.getId());

        ResultSet resultset = preparedStatement.executeQuery(requete);
        ArrayList<Order> orders = new ArrayList<>();

        while (resultset.next()){
            orders.add(Order.builder()
                    .id(resultset.getLong("OrderID"))
                    .payment(resultset.getDate("payment"))
                    .customer(customer).build());
        }

        resultset.close();
        preparedStatement.close();
        return orders;
    }


}
