package fr.emirashotel.model;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
public class Employee extends Person{

    private Date contractStart;

    private String role;

    @Builder
    public Employee(Long id, String name, String mail, Date dateOfBirth, String address, Date contractStart, String role) {
        super(id, name, mail, dateOfBirth, address);
        this.contractStart = contractStart;
        this.role = role;
    }
}
