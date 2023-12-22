package fr.emirashotel.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "room")
@Builder
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type")
    private RoomType type;

    @Column(name = "number")
    private int number;

    @OneToMany(mappedBy = "")
    private List<BookingRoom> rooms;
}
