package com.albo.challengealbomarvel.controller;

import com.albo.challengealbomarvel.dto.GenericApiRsDto;
import com.albo.challengealbomarvel.dto.marvel.ColaboratorsDto;
import com.albo.challengealbomarvel.dto.marvel.HeroesDto;
import com.albo.challengealbomarvel.service.MarvelExternalService;
import com.albo.challengealbomarvel.service.MarvelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/marvel")
public class MarvelController {
    private final MarvelService service;

    private final MarvelExternalService extService;

    public MarvelController(MarvelService service, MarvelExternalService extService) {
        this.service = service;
        this.extService = extService;
    }

    // TODO - Creado para cargar la DB manualmente para las pruebas durante el desarrollo, eliminar luego (tb eliminar el extService).
    @GetMapping("/prueba")
    @ResponseStatus(HttpStatus.OK)
    public void prubaSync() {
        log.debug("######## pruebaSync ########");

        extService.synchronizeData();
    }

    @GetMapping("/colaborators/{character}")
    @ResponseStatus(HttpStatus.OK)
    public GenericApiRsDto<ColaboratorsDto> getColaborators(@PathVariable("character") String character) {
        log.debug("getColaborators().character " + character);
        return new GenericApiRsDto<>(service.getColaborators(character));
    }

    @GetMapping("/characters/{character}")
    @ResponseStatus(HttpStatus.OK)
    public GenericApiRsDto<HeroesDto> getRelatedCharacters(@PathVariable("character") String character) {
        log.debug("getRelatedCharacters().character " + character);
        return new GenericApiRsDto<>(service.getRelatedCharacters(character));
    }
}
