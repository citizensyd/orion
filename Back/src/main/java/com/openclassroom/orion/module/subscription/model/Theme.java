package com.openclassroom.orion.module.subscription.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "themes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter
@Setter
@NoArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}

