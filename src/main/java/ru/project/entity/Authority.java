package ru.project.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Authority(String name) {
        this.name = name;
    }
}
