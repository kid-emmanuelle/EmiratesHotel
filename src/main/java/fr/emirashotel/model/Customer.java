package fr.emirashotel.model;

import fr.emirashotel.DatabaseManager;
import lombok.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
public class Customer extends Person{

    @Getter
    @Setter
    private Date joiningDate;

    private ArrayList<Order> orders;

    @Builder
    public Customer(Long id, String name, String mail, Date dateOfBirth, String address, Date joiningDate ) {
        super(id, name, mail, dateOfBirth, address);
        this.joiningDate = joiningDate;
    }

    public ArrayList<Order> getOrders(){
        if(this.orders == null){
            try {
                this.orders = DatabaseManager.getOrders(this);
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }
        return this.orders;
    }

    public void addOrder(Order order){
        getOrders().add(order);
    }


    public int getNumberOfBooking(){
        return 0;
    }


}
