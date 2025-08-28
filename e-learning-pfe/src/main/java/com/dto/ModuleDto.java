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

    private Long id;
    private String nom;
    private String plan;
    private String prix;
    private String description;
    private String image;
    private Long categorieId;
    private Long formateurId;
    private List<Long> chapitreIds;

    // === Mapping : Entity -> DTO ===
    public static ModuleDto toDto(Module module) {
        if (module == null) return null;

        return ModuleDto.builder()
                .id(module.getId())
                .nom(module.getNom())
                .plan(module.getPlan())
                .prix(module.getPrix())
                .description(module.getDescription())
                .image(module.getImage())
                .categorieId(module.getCategorie() != null ? module.getCategorie().getId() : null)
                .formateurId(module.getFormateur() != null ? module.getFormateur().getId() : null)
                .chapitreIds(module.getChapitres() != null
                        ? module.getChapitres().stream().map(c -> c.getId()).collect(Collectors.toList())
                        : null)
                .build();
    }

    // === Mapping : DTO -> Entity ===
    public static Module toEntity(ModuleDto dto) {
        if (dto == null) return null;

        Module module = new Module();
        module.setId(dto.getId());
        module.setNom(dto.getNom());
        module.setPlan(dto.getPlan());
        module.setPrix(dto.getPrix());
        module.setDescription(dto.getDescription());
        module.setImage(dto.getImage());
        return module;
    }
}
