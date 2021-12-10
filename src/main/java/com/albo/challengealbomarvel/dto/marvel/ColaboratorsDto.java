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
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = {"last_sync", "editors", "writers", "colorists"})
@Api(value = "Esquema que respresenta los colaboradores de los comics donde ha aprecido el heroe")
public class ColaboratorsDto {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    @JsonProperty("last_sync")
    @ApiParam(value = "Fecha de ultima sincronizacion con la fuente de Marvel en formato dd/MM/yyy hh:mm:ss")
    private LocalDateTime lastSync;

    @ApiParam(value = "Listado de nombres de los editores")
    private SortedSet<String> editors;
    @ApiParam(value = "Listado de nombres de los escritores")
    private SortedSet<String> writers;
    @ApiParam(value = "Listado de nombres de los coloristas")
    private SortedSet<String> colorists;
}