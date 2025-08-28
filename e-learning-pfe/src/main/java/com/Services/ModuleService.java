package com.Services;

import com.dto.ModuleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ModuleService {


    ModuleDto addModule(ModuleDto moduleDto) ;

    ModuleDto getModuleById(Long id) ;

    public List<ModuleDto> getModules();

    void deleteModuleById(Long id) ;

    ModuleDto uploadModuleImage(Long IdModule, MultipartFile image);


}
