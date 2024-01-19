package fr.emirashotel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Booking {

    private long id;

    private Customer customer;

}
