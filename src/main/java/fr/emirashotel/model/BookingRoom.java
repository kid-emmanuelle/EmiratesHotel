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

    // Données de test, pas des attributs, ne pas supprimer
    private long customerID;
    private Integer roomNumber;
    private long roomID;

    @Builder
    public BookingRoom(Long id, Customer customer, Date start, Date end, Room room) {
        super(id, customer);
        this.start = start;
        this.end = end;
        this.room = room;

        // Pareil
        this.customerID = customer.getId();
        this.roomNumber = room.getNumber();
        this.roomID = room.getId();
    }

    public Integer getRoomNumber(){
        return this.roomNumber;
    }

    public long getRoomID(){
        return this.roomID;
    }

    public long getCustomerID(){
        return this.customerID;
    }

    public Date getStart(){
        return this.start;
    }

    public Date getEnd(){
        return this.end;
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
