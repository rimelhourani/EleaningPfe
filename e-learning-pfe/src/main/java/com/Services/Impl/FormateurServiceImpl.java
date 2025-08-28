package com.Services.Impl;

import com.Services.FormateurService;
import com.configImage.ImageStorage;
import com.dto.ApprenantDto;
import com.dto.FormateurDto;
import com.entities.Formateur;
import com.repositories.ApprenantRepository;
import com.repositories.FormateurRepository;
import com.securite.models.Erole;
import com.securite.models.Role;
import com.securite.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class FormateurServiceImpl implements FormateurService {



    private final FormateurRepository formateurRepository;
    private final ImageStorage imageStorage;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;



    @Override
    public FormateurDto addFormateur(FormateurDto formateurDto)   {
     Formateur formateur = FormateurDto.toEntity(formateurDto);

        // Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(formateur.getPassword());
        formateur.setPassword(encodedPassword);

        // Attribuer le rôle "Formateur"
        Role formateurRole = roleRepository.findByName("formateur")
                .orElseThrow(() -> new RuntimeException("Role formateur not found"));
        formateur.setRole(Erole.FORMATEUR);

        // Sauvegarder
        formateur = formateurRepository.save(formateur);

        return FormateurDto.toDto(formateur);
    }


    @Override
    public FormateurDto getFormateurById(Long id)   {
        Formateur formateur = formateurRepository.findById(id).orElseThrow();
        return FormateurDto.toDto(formateur);
    }

    @Override
    public List<FormateurDto> getFormateurs() {
        List<Formateur> formateurs = formateurRepository.findAll();
        return formateurs.stream().map(FormateurDto::toDto).toList();
    }

    @Override
    public void deleteFormateurById(Long id) {
        getFormateurById(id);
        formateurRepository.deleteById(id);

    }



    @Override
    public FormateurDto updateFormateur(Long id, FormateurDto formateurDto) {
        Formateur existingFormateur = formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé avec l'ID : " + id));

        // Utiliser les bons noms de champs du DTO
        existingFormateur.setLastName(formateurDto.getNom());
        existingFormateur.setFirstName(formateurDto.getPrenom());
        existingFormateur.setUsername(formateurDto.getUsername());
        existingFormateur.setEmail(formateurDto.getEmail());
        existingFormateur.setPhoto(formateurDto.getPhoto());
        existingFormateur.setGrade(formateurDto.getGrade());

        // Si tu veux mettre à jour le password

        Formateur updatedFormateur = formateurRepository.save(existingFormateur);
        return FormateurDto.toDto(updatedFormateur);

    }


    @Override
    public String changePassword(Long id, String currentPassword, String newPassword) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Formateur not found"));

        if (!passwordEncoder.matches(currentPassword, formateur.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        formateur.setPassword(passwordEncoder.encode(newPassword));
        formateurRepository.save(formateur);

        return "Password updated successfully";
    }


    @Override
    public String updateEmail(Long id, String newEmail) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("formateur not found"));

        // Optionnel : vérifier si l'email existe déjà
        if (formateurRepository.findByEmail(newEmail).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        formateur.setEmail(newEmail);
        formateurRepository.save(formateur);

        return "Email updated successfully";
    }



    public ResponseEntity<Formateur> findbyId(Long id) {
        if (id == null) {
            return null;
        }
        return ResponseEntity.ok(formateurRepository.findById(id).get());

    }

    @Override
    public   FormateurDto uploadFormateurImage(Long IdFormateur, MultipartFile image){

        ResponseEntity<Formateur> formateurResponse = this.findbyId(IdFormateur);
        String imageName=imageStorage.store(image);

        String fileImageDownloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/formateur/downloadformateurimage/").path(imageName).toUriString();

        Formateur formateur = formateurResponse.getBody();

        if (formateur!=null)
            formateur.setPhoto(fileImageDownloadUrl);

        Formateur formateursaved = formateurRepository.save(formateur);
        new FormateurDto();
        return  FormateurDto.toDto(formateursaved);
    }
}
