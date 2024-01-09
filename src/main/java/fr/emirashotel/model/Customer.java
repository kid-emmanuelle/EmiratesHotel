package fr.emirashotel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
public class Customer extends Person{

    private Date joiningDate;

    private List<Order> orders;

    @Builder
    public Customer(Long id, String name, String mail, Date dateOfBirth, String address, Date joiningDate, List<Order> orders ) {
        super(id, name, mail, dateOfBirth, address);
        this.joiningDate = joiningDate;
        this.orders = orders;
    }


    public int getNumberOfBooking(){
        return 0;
    }


}
