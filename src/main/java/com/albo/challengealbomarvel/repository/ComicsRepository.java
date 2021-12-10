package com.albo.challengealbomarvel.repository;

import com.albo.challengealbomarvel.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComicsRepository extends JpaRepository<Comic, Long>, CrudRepository<Comic, Long> {
    // Obtiene un comic por nombre
    Optional<Comic> findByName(String comicName);
}
