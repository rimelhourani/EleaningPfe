package com.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "module")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String nom;
    private String plan;
    private String prix;
    private String description;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formateur")
    private Formateur formateur;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapitre> chapitres;

}