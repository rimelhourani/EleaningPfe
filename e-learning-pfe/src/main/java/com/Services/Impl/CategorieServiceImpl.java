package com.Services.Impl;

import com.Services.CategorieService;
import com.dto.CategorieDto;
import com.entities.Categorie;
import com.repositories.CategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    @Override
    public CategorieDto addCategorie(CategorieDto categorieDto)  {
        Categorie categorie = CategorieDto.toEntity(categorieDto);
        categorie = categorieRepository.save(categorie);
        return CategorieDto.toDto(categorie);
    }


    @Override
    public CategorieDto getCategorieById(Long id)  {
        Categorie categorie = categorieRepository.findById(id).orElseThrow();
        return CategorieDto.toDto(categorie);
    }

    @Override
    public List<CategorieDto> getCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        return categories.stream().map(CategorieDto::toDto).toList(); // Java 21 or Above
    }

    @Override
    public void deleteCategirieById(Long id)  {
        getCategorieById(id);
        categorieRepository.deleteById(id);

    }

}
