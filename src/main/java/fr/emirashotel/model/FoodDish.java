package fr.emirashotel.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodDish {

    private Long id;

    private String name;

    private String dishType;

    private String description;

    private float price;

}
