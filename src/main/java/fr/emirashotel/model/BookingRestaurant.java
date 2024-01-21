package fr.emirashotel.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class BookingRestaurant extends Booking {

    private FoodDish dish;
    private Integer quantity;
    private long customerID;
    private String foodName;
    private float foodPrice;
    private long foodID;

    @Builder
    public BookingRestaurant(long id, Customer customer, FoodDish dish, Integer quantity) {
        super(id, customer);
        this.dish = dish;
        this.quantity = quantity;
        this.customerID = customer.getId();
        this.foodName = dish.getName();
        this.foodPrice = dish.getPrice();
        this.foodID = dish.getID();
    }

    public String getFoodName(){
        return this.foodName;
    }

    public long getCustomerID(){
        return this.customerID;
    }

    public long getFoodID(){
        return this.foodID;
    }

    public Integer getQuantity(){
        return this.quantity;
    }

}
