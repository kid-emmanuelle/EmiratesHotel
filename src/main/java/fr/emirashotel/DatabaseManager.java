package fr.emirashotel;

import fr.emirashotel.model.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

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
        if(connection == null) return null;
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
        if(connection == null) return null;
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

    public static ArrayList<Room> getRooms() throws SQLException{
        if(connection == null) return null;
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM room";
        ResultSet resultset = statement.executeQuery(requete);
        ArrayList<Room> rooms = new ArrayList<>();

        while (resultset.next()){
            RoomType roomType = RoomType.Double;
            try {
                roomType = RoomType.valueOf(resultset.getString("type"));
            }catch (NoSuchElementException e){
                System.err.println("Erreur : Impossible de trouver le type:"+resultset.getString("type"));
            }
            rooms.add(Room.builder()
                    .id(resultset.getLong("RoomID"))
                    .type(roomType)
                    .number(resultset.getInt("number"))
                    .price(resultset.getFloat("price"))
                    .build()
            );
        }
        resultset.close();
        statement.close();
        return rooms;
    }

    /**
     * Récupérer liste des chambres qui sont disponible entre 2 dates
     */
    public static ArrayList<Room> getRooms(Date start, Date end) throws SQLException {
        if(connection == null) return null;
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM room " +
                "WHERE RoomID NOT IN ( " +
                "SELECT RoomID FROM room R " +
                "JOIN bookingroom ON (R.RoomID = room) " +
                "WHERE start >= ? and start <= ? " +
                "or end >= ? and end <= ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(requete);

        preparedStatement.setDate(1,new java.sql.Date(start.getTime()));
        preparedStatement.setDate(2,new java.sql.Date(start.getTime()));
        preparedStatement.setDate(3,new java.sql.Date(end.getTime()));
        preparedStatement.setDate(4,new java.sql.Date(end.getTime()));

        ResultSet resultset = preparedStatement.executeQuery();
        ArrayList<Room> rooms = new ArrayList<>();

        while (resultset.next()){
            RoomType roomType = RoomType.Double;
            try {
                roomType = RoomType.valueOf(resultset.getString("type"));
            }catch (NoSuchElementException e){
                System.err.println("Erreur : Impossible de trouver le type:"+resultset.getString("type"));
            }
            rooms.add(Room.builder()
                    .id(resultset.getLong("RoomID"))
                    .type(roomType)
                    .number(resultset.getInt("number"))
                    .price(resultset.getFloat("price"))
                    .build()
            );
        }
        resultset.close();
        statement.close();
        return rooms;
    }

    public static ArrayList<FoodDish> getDishes() throws SQLException{
        if(connection == null) return null;
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM fooddish";
        ResultSet resultset = statement.executeQuery(requete);
        ArrayList<FoodDish> dishes = new ArrayList<>();

        while (resultset.next()){
            dishes.add(FoodDish.builder()
                    .id(resultset.getLong("DishID"))
                    .dishType(resultset.getString("type"))
                    .description(resultset.getString("description"))
                    .price(resultset.getFloat("price"))
                    .build()
            );
        }
        resultset.close();
        statement.close();
        return dishes;
    }

    public static ArrayList<BookingRoom> bookingRooms() throws SQLException {
        if(connection == null) return null;
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM bookingroom JOIN customer C On (C.PersonID = customer) JOIN person P USING (PersonID) JOIN room R ON (R.RoomID = room)";
        ResultSet resultset = statement.executeQuery(requete);
        ArrayList<BookingRoom> bookingRooms = new ArrayList<>();

        while (resultset.next()){
            Customer customer = Customer.builder()
                    .id(resultset.getLong("personID"))
                    .name(resultset.getString("name"))
                    .mail(resultset.getString("mail"))
                    .dateOfBirth(resultset.getDate("dateOfBirth"))
                    .address(resultset.getString("address"))
                    .joiningDate(resultset.getDate("joiningDate"))
                    .build();

            RoomType roomType = RoomType.Double;
            try {
                roomType = RoomType.valueOf(resultset.getString("type"));
            }catch (NoSuchElementException e){
                System.err.println("Erreur : Impossible de trouver le type:"+resultset.getString("type"));
            }
            Room room = Room.builder()
                    .id(resultset.getLong("RoomID"))
                    .type(roomType)
                    .number(resultset.getInt("number"))
                    .price(resultset.getFloat("price"))
                    .build();

            bookingRooms.add(BookingRoom.builder()
                    .id(resultset.getLong("bookingID"))
                    .start(resultset.getDate("start"))
                    .end(resultset.getDate("end"))
                    .customer(customer)
                    .room(room)
                    .build()
            );
        }
        resultset.close();
        statement.close();
        return bookingRooms;
    }


    public static ArrayList<BookingRestaurant> bookingDishes() throws SQLException {
        if(connection == null) return null;
        Statement statement = connection.createStatement();
        String requete = "SELECT * FROM bookingrestaurant JOIN customer C On (C.PersonID = customer) JOIN person P USING (PersonID) JOIN fooddish F ON (F.DishID = dish)";
        ResultSet resultset = statement.executeQuery(requete);
        ArrayList<BookingRestaurant> bookingRestaurants = new ArrayList<>();

        while (resultset.next()){
            Customer customer = Customer.builder()
                    .id(resultset.getLong("personID"))
                    .name(resultset.getString("name"))
                    .mail(resultset.getString("mail"))
                    .dateOfBirth(resultset.getDate("dateOfBirth"))
                    .address(resultset.getString("address"))
                    .joiningDate(resultset.getDate("joiningDate"))
                    .build();

            FoodDish dish = FoodDish.builder()
                    .id(resultset.getLong("DishID"))
                    .dishType(resultset.getString("type"))
                    .description(resultset.getString("description"))
                    .price(resultset.getFloat("price"))
                    .build();


            bookingRestaurants.add(BookingRestaurant.builder()
                    .id(resultset.getLong("bookingID"))
                    .customer(customer)
                    .dish(dish)
                    .build()
            );
        }
        resultset.close();
        statement.close();
        return bookingRestaurants;
    }

    //Add
//    public static boolean addEmployee(Employee employee){}


}
