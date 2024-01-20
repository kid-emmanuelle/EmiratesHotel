package fr.emirashotel.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room {

    private Long id;

    private RoomType type;

    private int number;

    private float price;
}
