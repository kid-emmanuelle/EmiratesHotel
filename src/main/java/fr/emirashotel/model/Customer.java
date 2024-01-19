package fr.emirashotel.model;

import fr.emirashotel.DatabaseManager;
import lombok.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Customer extends Person{

    private Date joiningDate;


    @Builder
    public Customer(Long id, String name, String mail, Date dateOfBirth, String address, Date joiningDate ) {
        super(id, name, mail, dateOfBirth, address);
        this.joiningDate = joiningDate;
    }


    public int getNumberOfBooking(){
        return 0;
    }


}
