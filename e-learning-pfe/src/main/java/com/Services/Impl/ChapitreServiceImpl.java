package com.Services.Impl;
import com.entities.Module;
import com.Services.ChapitreService;
import com.dto.ChapitreDto;
import com.entities.Chapitre;
import com.filestorage.FileStorageService;
import com.repositories.ChapitreRepository;
import com.repositories.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ChapitreServiceImpl implements ChapitreService {



    private final ChapitreRepository chapitreRepository;
    private final ModuleRepository moduleRepository;
    private final FileStorageService fileStorageService;


    @Override
    public ChapitreDto addChapitre(ChapitreDto dto) {
        // 1️⃣ Récupérer le module correspondant à l'id fourni
        Module module = moduleRepository.findById(dto.getModuleId()).orElse(null);

        if (module == null) {
            // module inexistant → tu peux soit créer un module vide, soit retourner null
            // ici on retourne null
            return null;
        }

        // 2️⃣ Convertir le DTO en entité
        Chapitre chapitre = ChapitreDto.toEntity(dto);

        // 3️⃣ Associer le module au chapitre
        chapitre.setModule(module);

        // 4️⃣ Sauvegarder le chapitre
        Chapitre saved = chapitreRepository.save(chapitre);

        // 5️⃣ Retourner le DTO
        return ChapitreDto.toDto(saved);
    }


    @Override
    public List<ChapitreDto> getAll() {
        return chapitreRepository.findAll().stream()
                .map(ChapitreDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChapitreDto updateChapitre(Long id, ChapitreDto dto) {
        Chapitre existing = chapitreRepository.findById(id)
                .orElse(null);

        existing.setTitre(dto.getTitre());
        existing.setDescription(dto.getDescription());
        existing.setContenu(dto.getContenu());


        return ChapitreDto.toDto(chapitreRepository.save(existing));
    }

    @Override
    public void deleteChapitre(Long id) {
        chapitreRepository.deleteById(id);
    }

    @Override
    public List<ChapitreDto> getByModuleId(Long moduleId) {
        return chapitreRepository.findByModuleId(moduleId).stream()
                .map(ChapitreDto::toDto)
                .collect(Collectors.toList());
    }





    public ChapitreDto uploadFile(Long chapitreId, MultipartFile file) throws IOException {
        Chapitre support = chapitreRepository.findById(chapitreId)
                .orElseThrow(() -> new RuntimeException("Support not found"));

        // Exemple simple de stockage local
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.copy(file.getInputStream(), path);

        support.setContenu(fileName);
        chapitreRepository.save(support);
        return ChapitreDto.toDto(support);
    }



}
