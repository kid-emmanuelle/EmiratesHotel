package fr.emirashotel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "paid")
    private Date payement;

    @OneToMany(mappedBy = "booking")
    private List<Booking> bookings;

    @ManyToOne
    private Customer customer;


    public int calcPrice(){
        return 0;
    }

}
