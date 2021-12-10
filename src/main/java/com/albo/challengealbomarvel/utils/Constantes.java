package com.albo.challengealbomarvel.utils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constantes {
    public static final Map<String, Long> SEARCH_KEYS = Stream.of(new AbstractMap.SimpleEntry<>("ironman", 1009368L), new AbstractMap.SimpleEntry<>("capamerica", 1009220L))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static final String[] ROLES = {"colorist", "writer", "editor"};
}
