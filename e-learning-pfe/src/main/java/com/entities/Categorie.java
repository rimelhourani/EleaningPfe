package com.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categorie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nom;
    private String description;


//    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Module> modules;
}
