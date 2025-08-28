package com.controllers;

import com.Services.ModuleService;
import com.configImage.ImageStorage;
import com.dto.ModuleDto;
import com.exeptions.ModuleNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
@RestController
@RequestMapping("/api/v1/module")
@RequiredArgsConstructor
@Log
public class ModuleController {
    private final ModuleService moduleService;
    private final ImageStorage imageStorage;


    @PostMapping("/addmodule")
    public ResponseEntity<ModuleDto> addModule(@RequestBody final ModuleDto moduleDto) {
        try {
            return new ResponseEntity<>(moduleService.addModule(moduleDto), HttpStatus.CREATED);
        } catch (ModuleNotFoundException e) {
            log.info(String.format("module with id = %s not found", moduleDto.getId()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getmodulebyid/{id}")
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable("id") final Long id) {
        try {
            return new ResponseEntity<>(moduleService.getModuleById(id), HttpStatus.OK);
        } catch (ModuleNotFoundException e) {
            log.info(String.format("Module with id = %s not found", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getallmodule")
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        return new ResponseEntity<>(moduleService.getModules(), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteModuleById(@PathVariable("id") final Long id) {
        try {
            moduleService.deleteModuleById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ModuleNotFoundException e) {
            log.info(String.format("module with id = %s not found", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(path = "/uploadImage/{IdModule}" , consumes = MULTIPART_FORM_DATA_VALUE)
    public ModuleDto uploadModuleImage(@PathVariable("IdModule")  Long IdModule,
                                             @RequestPart(value = "image") MultipartFile image) {
        return moduleService.uploadModuleImage(IdModule, image);
    }

    @GetMapping("/downloadmoduleimage/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageName, HttpServletRequest request) {
        return this.imageStorage.downloadUserImage(imageName, request);
    }



}
