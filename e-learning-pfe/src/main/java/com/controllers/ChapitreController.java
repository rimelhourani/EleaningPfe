package com.controllers;

import com.Services.ChapitreService;
import com.dto.ChapitreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/v1/chapitre")
@RequiredArgsConstructor
@Log
public class ChapitreController {


    private final ChapitreService chapitreService;


    @PostMapping("/save")
    public ResponseEntity<ChapitreDto> addChapitreData(@RequestBody ChapitreDto dto) throws IOException {
        return ResponseEntity.ok(chapitreService.addChapitre(dto));
    }


    @GetMapping("/getall")
    public ResponseEntity<List<ChapitreDto>> getAll() {
        return ResponseEntity.ok(chapitreService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChapitreDto> update(@PathVariable Long id, @RequestBody ChapitreDto dto) {
        return ResponseEntity.ok(chapitreService.updateChapitre(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chapitreService.deleteChapitre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getchapitrebymodule/{moduleId}")
    public ResponseEntity<List<ChapitreDto>> getByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(chapitreService.getByModuleId(moduleId));
    }


    @PostMapping(value = "/upload-file/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChapitreDto> uploadFile(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(chapitreService.uploadFile(id, file));
    }



}
