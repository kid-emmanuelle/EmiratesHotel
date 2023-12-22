package fr.emirashotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "booking")
public abstract class Booking {

    @Id
    @GeneratedValue
    private Long id;



}
