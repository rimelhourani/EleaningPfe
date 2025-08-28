package com.Services.Impl;

import com.Services.ModuleService;
import com.configImage.ImageStorage;
import com.dto.ModuleDto;
import com.entities.Categorie;
import com.entities.Formateur;
import com.entities.Module;
import com.repositories.CategorieRepository;
import com.repositories.FormateurRepository;
import com.repositories.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private  final CategorieRepository categorieRepository;
    private final ImageStorage imageStorage;
    private final FormateurRepository formateurRepository;




    @Override
    public ModuleDto addModule(ModuleDto dto) {
        // 1️⃣ Récupérer la catégorie par son id (si fournie)
        Categorie categorie = null;
        if (dto.getCategorieId() != null) {
            categorie = categorieRepository.findById(dto.getCategorieId()).orElse(null);
        }

        // 2️⃣ Récupérer le formateur par son id (si fourni)
        Formateur formateur = null;
        if (dto.getFormateurId() != null) {
            formateur = formateurRepository.findById(dto.getFormateurId()).orElse(null);
        }

        // 3️⃣ Convertir le DTO en entité Module
        Module module = ModuleDto.toEntity(dto);

        // 4️⃣ Associer catégorie et formateur
        module.setCategorie(categorie);
        module.setFormateur(formateur);

        // 5️⃣ Sauvegarder le module
        Module saved = moduleRepository.save(module);

        // 6️⃣ Retourner le DTO
        return ModuleDto.toDto(saved);
    }


    @Override
    public ModuleDto getModuleById(Long id)  {
        Module module = moduleRepository.findById(id).orElseThrow();
        return ModuleDto.toDto(module);
    }

    @Override
    public List<ModuleDto> getModules() {
        List<Module> modules = moduleRepository.findAll();
        return modules.stream().map(ModuleDto::toDto).toList(); // Java 21 or Above
    }

    @Override
    public void deleteModuleById(Long id)  {
        getModuleById(id);
        moduleRepository.deleteById(id);

    }


    public ResponseEntity<Module> findbyId(Long id) {
        if (id == null) {
            return null;
        }
        return ResponseEntity.ok(moduleRepository.findById(id).get());

    }

    @Override
    public ModuleDto uploadModuleImage(Long IdBlog, MultipartFile image) {

        ResponseEntity<Module> moduleResponse = this.findbyId(IdBlog);
        String imageName=imageStorage.store(image);

        String fileImageDownloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/module/downloadmoduleimage/").path(imageName).toUriString();

        Module module = moduleResponse.getBody();

        if (module!=null)
            module.setImage(fileImageDownloadUrl);

        Module modulesaved = moduleRepository.save(module);
        new ModuleDto();
        return  ModuleDto.toDto(modulesaved);
    }
}
