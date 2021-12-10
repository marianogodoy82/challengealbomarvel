package com.albo.challengealbomarvel.collectors;

import com.albo.challengealbomarvel.model.Comic;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;

@Slf4j
@Getter
public class ColaboratorCollector implements Consumer<Comic> {

    private SortedSet<String> editors;
    private SortedSet<String> colorists;
    private SortedSet<String> writers;

    public ColaboratorCollector() {
        this.editors = new TreeSet<>();
        this.colorists = new TreeSet<>();
        this.writers = new TreeSet<>();
    }

    @Override
    public void accept(Comic comic) {
        log.debug("Comic= " + comic);

        comic.getColaborators().forEach(i -> {
            switch (i.getRole()) {
                case "editor":
                    this.editors.add(i.getName());
                    break;
                case "colorist":
                    this.colorists.add(i.getName());
                    break;
                case "writer":
                    this.writers.add(i.getName());
                    break;
                default:
                    break;
            }
        });
    }

    public ColaboratorCollector combine(ColaboratorCollector other) {
        return this;
    }

    public ColaboratorCollector finish() {
        return this;
    }
}
