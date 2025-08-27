package com.dto;

import com.entities.Categorie;
import com.entities.Module;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CategorieDto {

    private int id;
    private String nom;

    // ⚠️ Attention : inclure la liste des modules peut créer une boucle infinie
    // (car ModuleDto contient déjà CategorieDto).
    // Pour éviter ça, on peut exposer seulement les IDs ou bien utiliser un DTO léger.
    private List<ModuleDto> modules;

    // ----------------- MAPPER -----------------

    public static CategorieDto toDto(Categorie categorie) {
        if (categorie == null) return null;

        return CategorieDto.builder()
                .id(categorie.getId())
                .nom(categorie.getNom())
                .modules(categorie.getModules() != null
                        ? categorie.getModules().stream()
                        .map(m -> ModuleDto.builder()
                                .id(m.getId())
                                .nom(m.getNom())
                                .plan(m.getPlan())
                                .prix(m.getPrix())
                                .description(m.getDescription())
                                .build()
                        ).collect(Collectors.toList())
                        : null)
                .build();
    }

    public static Categorie toEntity(CategorieDto dto) {
        if (dto == null) return null;

        Categorie categorie = new Categorie();
        categorie.setId(dto.getId());
        categorie.setNom(dto.getNom());

        // ⚠️ Pour éviter boucle infinie, on ne reconstruit pas modules ici,
        // sauf si c’est vraiment nécessaire
        if (dto.getModules() != null) {
            categorie.setModules(dto.getModules().stream()
                    .map(ModuleDto::toEntity)
                    .collect(Collectors.toList()));
        }

        return categorie;
    }
}

