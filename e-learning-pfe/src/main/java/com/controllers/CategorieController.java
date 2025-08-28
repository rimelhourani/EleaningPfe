package com.controllers;

import com.Services.CategorieService;
import com.dto.CategorieDto;
import com.exeptions.CategorieNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/categorie")
@RequiredArgsConstructor
@Log
public class CategorieController {



    private final CategorieService categorieService;



    @PostMapping("/addcategorie")
    public ResponseEntity<CategorieDto> addCategorie(@RequestBody final CategorieDto categorieDto) {
        try {
            return new ResponseEntity<>(categorieService.addCategorie(categorieDto), HttpStatus.CREATED);
        } catch (CategorieNotFoundException e) {
            log.info(String.format("caegorie with id = %s not found", categorieDto.getId()));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcategoriebyid/{id}")
    public ResponseEntity<CategorieDto> getCategorieById(@PathVariable("id") final Long id) {
        try {
            return new ResponseEntity<>(categorieService.getCategorieById(id), HttpStatus.OK);
        } catch (CategorieNotFoundException e) {
            log.info(String.format("categorie with id = %s not found", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getallCategorie")
    public ResponseEntity<List<CategorieDto>> getAllCategories() {
        return new ResponseEntity<>(categorieService.getCategories(), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategorieById(@PathVariable("id") final Long id) {
        try {
            categorieService.deleteCategirieById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CategorieNotFoundException e) {
            log.info(String.format("categorie with id = %s not found", id));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
