package com.repositories;

import com.entities.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormateurRepository extends JpaRepository<Formateur, Long>
{
    Optional<Formateur> findByEmail(String email);
}
