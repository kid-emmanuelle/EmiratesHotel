package fr.emirashotel.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public abstract class Person {

    private Long id;

    private String name;

    private String mail;

    private Date dateOfBirth;

    private String address;


}
