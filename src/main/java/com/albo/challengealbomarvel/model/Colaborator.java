package com.albo.challengealbomarvel.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "colaborators",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "role"})})
public class Colaborator {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    private String name;
    private String role;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "colaborators", cascade = {CascadeType.PERSIST})
    private Set<Comic> comics = new HashSet<>(0);

    public Colaborator(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Colaborator() {

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

            comic.addColaborator(this);
        }
    }

    public void removeComic(Comic comic) {

        if (comics.contains(comic)) {
            comics.remove(comic);

            comic.removeColaborator(this);
        }
    }
}
