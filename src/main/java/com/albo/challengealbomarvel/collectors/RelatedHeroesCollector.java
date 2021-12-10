package com.albo.challengealbomarvel.collectors;

import com.albo.challengealbomarvel.dto.marvel.RelatedHeroesDto;
import com.albo.challengealbomarvel.model.Comic;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class RelatedHeroesCollector implements Consumer<Comic> {

    String mainHeroName;

    private final SortedSet<RelatedHeroesDto> characters;

    public RelatedHeroesCollector(String mainHeroName) {
        this.characters = new TreeSet<>();
        this.mainHeroName = mainHeroName;
    }

    @Override
    public void accept(Comic comic) {
        log.debug("Comic= " + comic);

        comic.getHeroes().forEach(i -> {
            RelatedHeroesDto relatedHero = new RelatedHeroesDto();
            if (!i.getFullName().equalsIgnoreCase(mainHeroName)) {
                relatedHero.setName(i.getFullName());
                relatedHero.setComics(i.getComics().stream().map(Comic::getName).collect(Collectors.toSet()));

                this.characters.add(relatedHero);
            }
        });
    }

    public RelatedHeroesCollector combine(RelatedHeroesCollector other) {
        return this;
    }

    public RelatedHeroesCollector finish() {
        return this;
    }
}