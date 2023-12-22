package fr.emirashotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "employee")
public class Employee extends Person{

    @Column(name = "contractstart")
    private Date contractStart;

    @Column(name = "role")
    private String role;

    @Builder
    public Employee(Long id, String name, String mail, Date dateOfBirth, String address, Date contractStart, String role) {
        super(id, name, mail, dateOfBirth, address);
        this.contractStart = contractStart;
        this.role = role;
    }
}
