package com.Services;

import com.dto.CategorieDto;

import java.util.List;

public interface CategorieService {

    CategorieDto addCategorie(CategorieDto categorieDto);

    CategorieDto getCategorieById(Long id);

    public List<CategorieDto> getCategories();

    void deleteCategirieById(Long id);
}
