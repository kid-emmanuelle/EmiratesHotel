package fr.emirashotel.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "person")
public abstract class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "dateofbirth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "address", nullable = false)
    private String address;


}
