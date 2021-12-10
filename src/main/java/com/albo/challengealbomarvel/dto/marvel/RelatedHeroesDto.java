package com.albo.challengealbomarvel.dto.marvel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = {"character", "comics"})
@Api(value = "Esquema que representa el personaje que han colaborado con el heroe")
public class RelatedHeroesDto implements Comparable {

    private static final long serialVersionUID = 1L;

    @ApiParam(value = "Nombre de personaje", example = "Spider-Man")
    @JsonProperty("character")
    private String name;

    @ApiParam(value = "Listado de nombre de los comics en los que el personaje ha colaborado con el heroe")
    private Set<String> comics;

    @Override
    public int compareTo(Object o) {
        RelatedHeroesDto other = (RelatedHeroesDto) o;

        if (this.getName().equalsIgnoreCase(other.getName())) {
            return -1;
        }

        for (String comicName : this.getComics()) {
            if (other.getComics().contains(comicName)) {
                return -1;
            }
        }

        return 0;
    }
}