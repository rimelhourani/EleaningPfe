package com.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "chapitre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Chapitre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String contenu;
    private String titre;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_module")
    private Module module;
}

