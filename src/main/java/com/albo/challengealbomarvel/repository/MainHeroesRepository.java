package com.albo.challengealbomarvel.repository;

import com.albo.challengealbomarvel.model.MainHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainHeroesRepository extends JpaRepository<MainHero, Long> {

    // Obtiene un personaje por el nombre.
    Optional<MainHero> findByName(String name);
}
