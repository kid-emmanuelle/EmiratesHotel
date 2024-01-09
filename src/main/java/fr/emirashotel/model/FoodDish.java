package fr.emirashotel.model;

import lombok.Data;

@Data
public class FoodDish {

    private Long id;

    private String name;

    private DishType dishType;

    private String description;

    private float price;

}
