package com.dto;

import com.entities.Formateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FormateurDto {

    private Long id;
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String grade;
    private String photo;
    private String password;




    public static FormateurDto toDto(Formateur formateur) {
        if (formateur == null) return null;

        return FormateurDto.builder()
                .id(formateur.getId())
                .nom(formateur.getFirstName())
                .prenom(formateur.getLastName())
                .username(formateur.getUsername())
                .password(formateur.getPassword())
                .email(formateur.getEmail())
                .grade(formateur.getGrade())
                .photo(formateur.getPhoto())
                .build();
    }

    public static Formateur toEntity(FormateurDto dto) {
        if (dto == null) return null;

        Formateur formateur = new Formateur();
        formateur.setId(dto.getId());
        formateur.setFirstName(dto.getPrenom());
        formateur.setLastName(dto.getNom());
        formateur.setUsername(dto.getUsername());
        formateur.setPassword(dto.getPassword());
        formateur.setEmail(dto.getEmail());
        formateur.setGrade(dto.getGrade());
        formateur.setPhoto(dto.getPhoto());

        return formateur;
    }


}
