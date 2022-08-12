package ru.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role{

    @Id
    @NotNull
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Column(name = "name",unique = true)
    private String name;
    public String getName() {
        return name;
    }
}
