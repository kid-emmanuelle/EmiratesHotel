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

    @Builder
    public FoodDish(long id, String name, String dishType, String description, float price){
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.description = description;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public String getType(){
        return this.dishType;
    }

    public String getDescription(){
        return this.description;
    }

    public long getID(){
        return this.id;
    }

    public float getPrice(){
        return this.price;
    }

}
