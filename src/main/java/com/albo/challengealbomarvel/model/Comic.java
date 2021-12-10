package com.albo.challengealbomarvel.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "comics")
public class Comic {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    private String name;

    @ManyToMany(fetch = EAGER, cascade = {PERSIST, MERGE})
    @JoinTable(name = "heroes_comics",
            joinColumns = @JoinColumn(name = "comic_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "hero_id", nullable = false, updatable = false))
    private Set<Hero> heroes = new HashSet<>(0);

    @ManyToMany(fetch = EAGER, cascade = {PERSIST, MERGE})
    @JoinTable(name = "comics_colaborator",
            joinColumns = @JoinColumn(name = "comic_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "colaborator_id", nullable = false, updatable = false))
    private Set<Colaborator> colaborators = new HashSet<>(0);

    public Comic(String name) {
        this.name = name;
    }

    public Comic() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(Set<Hero> heroes) {
        this.heroes = heroes;
    }

    public Set<Colaborator> getColaborators() {
        return colaborators;
    }

    public void setColaborators(Set<Colaborator> colaborators) {
        this.colaborators = colaborators;
    }

    public void addHero(Hero hero) {
        if (!heroes.contains(hero)) {
            heroes.add(hero);

            hero.addComic(this);
        }
    }

    public void removeHero(Hero hero) {
        if (heroes.contains(hero)) {
            heroes.remove(hero);

            hero.removeComic(this);
        }
    }

    public void addColaborator(Colaborator colaborator) {
        if (!colaborators.contains(colaborator)) {
            colaborators.add(colaborator);

            colaborator.addComic(this);
        }
    }

    public void removeColaborator(Colaborator colaborator) {
        if (colaborators.contains(colaborator)) {
            colaborators.remove(colaborator);

            colaborator.removeComic(this);
        }
    }
}
