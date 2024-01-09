package fr.emirashotel.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {

    private Long id;

    private Date payement;

    private List<Booking> bookings;

    private Customer customer;


    public int calcPrice(){
        return 0;
    }

}
