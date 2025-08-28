package com.repositories;

import com.entities.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapitreRepository  extends JpaRepository<Chapitre, Long> {
    List<Chapitre> findByModuleId(Long moduleId);
}
