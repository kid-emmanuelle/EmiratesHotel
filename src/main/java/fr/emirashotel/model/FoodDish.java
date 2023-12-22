package fr.emirashotel.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "fooddish")
public class FoodDish {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private DishType dishType;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

}
