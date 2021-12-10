package com.albo.challengealbomarvel.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "heroes")
public class Hero {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String fullName;

    @ManyToMany(fetch = EAGER, mappedBy = "heroes", cascade = {PERSIST})
    private Set<Comic> comics = new HashSet<>(0);

    public Hero(String fullName) {
        this.fullName = fullName;
    }

    public Hero() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Comic> getComics() {
        return comics;
    }

    public void setComics(Set<Comic> comics) {
        this.comics = comics;
    }


    public void addComic(Comic comic) {
        if (!comics.contains(comic)) {
            comics.add(comic);

            comic.addHero(this);
        }
    }

    public void removeComic(Comic comic) {
        if (comics.contains(comic)) {
            comics.remove(comic);

            comic.removeHero(this);
        }
    }
}
