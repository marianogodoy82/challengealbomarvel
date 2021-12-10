package com.albo.challengealbomarvel.repository;

import com.albo.challengealbomarvel.model.Colaborator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColaboratorsRepository extends CrudRepository<Colaborator, Long> {
    // Obtiene un colaborador por nombre
    Optional<Colaborator> findByName(String shortName);

    // Obtiene un colaborador por nombre y rol
    Optional<Colaborator> findByNameAndRole(String shortName, String role);
}
