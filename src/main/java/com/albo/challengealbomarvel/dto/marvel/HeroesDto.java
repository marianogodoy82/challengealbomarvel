package com.albo.challengealbomarvel.dto.marvel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.SortedSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = {"last_sync", "characters"})
@Api(value = "Esquema que representa el listado de personajes que han colaborado con el heroe")
public class HeroesDto {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonProperty("last_sync")
    @ApiParam(value = "Fecha de ultima sincronizacion con la fuente de Marvel en formato dd/MM/yyy hh:mm:ss", example = "04/11/2021 12:00:00")
    private LocalDateTime lastSync;

    @ApiParam(value = "Lista de los personajes que han colaborado con el heroe")
    private SortedSet<RelatedHeroesDto> characters;
}
