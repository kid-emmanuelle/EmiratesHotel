package fr.emirashotel.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class BookingRestaurant extends Booking {

    private FoodDish dish;

    @Builder
    public BookingRestaurant(long id, Customer customer, FoodDish dish) {
        super(id, customer);
        this.dish = dish;
    }

}
