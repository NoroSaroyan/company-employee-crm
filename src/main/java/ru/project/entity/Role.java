package ru.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role{
    @Id
    @NotNull
    @Column(name = "name",unique = true)
    String name;

    public String getName() {
        return name;
    }
}
