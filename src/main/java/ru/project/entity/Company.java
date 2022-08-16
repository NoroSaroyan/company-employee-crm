package ru.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true)
    @NotNull
    private String name;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @OneToMany
    private List<Employee> employees;


}
