package fr.emirashotel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookingRoom extends Booking{

    private String description;

    private Date start;

    private Date end;

    private float price;

    private List<Room> rooms;

}
