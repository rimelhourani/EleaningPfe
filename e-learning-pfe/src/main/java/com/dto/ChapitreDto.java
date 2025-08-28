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

    private long id;
    private String contenu;
    private String titre;
    private String description;
    private long moduleId;

    // ----------------- MAPPER -----------------

    public static ChapitreDto toDto(Chapitre chapitre) {
        if (chapitre == null) return null;

        return ChapitreDto.builder()
                .id(chapitre.getId())
                .contenu(chapitre.getContenu())
                .titre(chapitre.getTitre())
                .description(chapitre.getDescription())
                .moduleId(chapitre.getModule() != null ? chapitre.getModule().getId() : null)
                .build();
    }

    public static Chapitre toEntity(ChapitreDto dto) {
        if (dto == null) return null;

        Chapitre chapitre = new Chapitre();
        chapitre.setId(dto.getId());
        chapitre.setContenu(dto.getContenu());
        chapitre.setTitre(dto.getTitre());
        chapitre.setDescription(dto.getDescription());
            Module module = new Module();
            module.setId(dto.getModuleId());
            chapitre.setModule(module);


        return chapitre;
    }
}
