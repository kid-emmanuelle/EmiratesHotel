package fr.emirashotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "bookingroom")
public class BookingRoom extends Booking{

    @Column(name = "description")
    private String description;

    @Column(name = "datestart")
    private Date start;

    @Column(name = "dateend")
    private Date end;

    @Column(name = "price")
    private float price;

    @OneToMany(mappedBy = "bookingroom")
    private List<Room> rooms;

}
