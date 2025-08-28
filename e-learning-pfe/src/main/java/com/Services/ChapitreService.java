package com.Services;

import com.dto.ChapitreDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChapitreService {


    ChapitreDto addChapitre(ChapitreDto dto) ;
    List<ChapitreDto> getAll();
    ChapitreDto updateChapitre(Long id, ChapitreDto dto);
    void deleteChapitre(Long id);
    List<ChapitreDto> getByModuleId(Long moduleId);
    ChapitreDto uploadFile(Long chapitreId, MultipartFile file) throws IOException;

}
