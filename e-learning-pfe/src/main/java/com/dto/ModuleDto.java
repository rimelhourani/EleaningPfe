package com.dto;

import com.entities.Module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ModuleDto {

    private int id;
    private String nom;
    private String plan;
    private String prix;
    private String description;

    private CategorieDto categorie;   // ou juste categorieId si tu veux simplifier
    private FormateurDto formateur;   // tu as déjà créé FormateurDto
    private List<ChapitreDto> chapitres;

    // ----------------- MAPPER -----------------

    public static ModuleDto toDto(Module module) {
        if (module == null) return null;

        return ModuleDto.builder()
                .id(module.getId())
                .nom(module.getNom())
                .plan(module.getPlan())
                .prix(module.getPrix())
                .description(module.getDescription())
                .categorie(CategorieDto.toDto(module.getCategorie()))
                .formateur(FormateurDto.toDto(module.getFormateur()))
                .chapitres(module.getChapitres() != null
                        ? module.getChapitres().stream()
                        .map(ChapitreDto::toDto)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    public static Module toEntity(ModuleDto dto) {
        if (dto == null) return null;

        Module module = new Module();
        module.setId(dto.getId());
        module.setNom(dto.getNom());
        module.setPlan(dto.getPlan());
        module.setPrix(dto.getPrix());
        module.setDescription(dto.getDescription());
        module.setCategorie(CategorieDto.toEntity(dto.getCategorie()));
        module.setFormateur(FormateurDto.toEntity(dto.getFormateur()));
        module.setChapitres(dto.getChapitres() != null
                ? dto.getChapitres().stream()
                .map(ChapitreDto::toEntity)
                .collect(Collectors.toList())
                : null);

        return module;
    }
}

