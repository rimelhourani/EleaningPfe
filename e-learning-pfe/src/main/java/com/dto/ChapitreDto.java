package com.dto;

import com.entities.Chapitre;
import com.entities.Module;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ChapitreDto {

    private int id;
    private String contenu;
    private Integer moduleId; // ⚠️ pour éviter la boucle infinie avec ModuleDto

    // ----------------- MAPPER -----------------

    public static ChapitreDto toDto(Chapitre chapitre) {
        if (chapitre == null) return null;

        return ChapitreDto.builder()
                .id(chapitre.getId())
                .contenu(chapitre.getContenu())
                .moduleId(chapitre.getModule() != null ? chapitre.getModule().getId() : null)
                .build();
    }

    public static Chapitre toEntity(ChapitreDto dto) {
        if (dto == null) return null;

        Chapitre chapitre = new Chapitre();
        chapitre.setId(dto.getId());
        chapitre.setContenu(dto.getContenu());

        // Ici on met juste un Module avec l'id (lazy load par le service)
        if (dto.getModuleId() != null) {
            Module module = new Module();
            module.setId(dto.getModuleId());
            chapitre.setModule(module);
        }

        return chapitre;
    }
}
