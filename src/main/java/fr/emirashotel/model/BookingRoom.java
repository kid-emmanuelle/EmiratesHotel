package fr.emirashotel.model;

import fr.emirashotel.DatabaseManager;
import lombok.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class BookingRoom extends Booking{

    private Date start;

    private Date end;

    private Room room;

    @Builder
    public BookingRoom(Long id, Customer customer, Date start, Date end, Room room/*, Order order*/) {
        super(id, customer);
        this.start = start;
        this.end = end;
        this.room = room;
//        this.order = order;
    }
/*
    public ArrayList<Room> getRooms(){
        if(this.rooms == null){
            try {
                this.rooms = DatabaseManager.getRooms(this);
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }
        return this.rooms;
    }*/
/*
    public void addRoom(Room room){
        getRooms().add(room);
    }*/

}
