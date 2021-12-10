package com.albo.challengealbomarvel.service;

import com.albo.challengealbomarvel.model.Colaborator;
import com.albo.challengealbomarvel.model.Comic;
import com.albo.challengealbomarvel.model.Hero;
import com.albo.challengealbomarvel.model.MainHero;
import com.albo.challengealbomarvel.repository.ColaboratorsRepository;
import com.albo.challengealbomarvel.repository.ComicsRepository;
import com.albo.challengealbomarvel.repository.HeroesRepository;
import com.albo.challengealbomarvel.repository.MainHeroesRepository;
import com.albo.challengealbomarvel.utils.Constantes;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class MarvelExternalService {
    @Value("${rest.marvel.key.public}")
    private String publicKey;
    @Value("${rest.marvel.key.private}")
    private String privateKey;
    @Value("${rest.marvel.host}")
    private String marvelHost;
    @Value("${rest.marvel.limit}")
    private int limit;

    private final RestTemplate marvelApiRestTemplate;
    private final ComicsRepository comicsRepository;
    private final MainHeroesRepository mainHeroesRepository;
    private final HeroesRepository heroesRepository;
    private final ColaboratorsRepository colaboratorsRepository;


    public MarvelExternalService(RestTemplate marvelApiRestTemplate, ComicsRepository comicsRepository, HeroesRepository heroesRepository, MainHeroesRepository mainHeroesRepository, ColaboratorsRepository colaboratorsRepository) {
        this.marvelApiRestTemplate = marvelApiRestTemplate;
        this.comicsRepository = comicsRepository;
        this.heroesRepository = heroesRepository;
        this.mainHeroesRepository = mainHeroesRepository;
        this.colaboratorsRepository = colaboratorsRepository;
    }

    @PostConstruct
    private void setUp() {
        this.synchronizeData();
    }

    /**
     * Metodo que se encarga de ejecutar la llamada a la api de marvel y actualizar la DB con dicha informacion.
     */
    //@Scheduled(cron = "${rest.cron.expression}")
    public void synchronizeData() {
        log.info("Iniciando proceso de sincronizacion....");
        log.info("Horario de comienzo de la Sincronizacion: {}....", LocalDateTime.now());

        List<MainHero> syncHeroes = mainHeroesRepository.findAll();
        log.info("Personajes a Sincronizar= {}", syncHeroes);

        syncHeroes.forEach(hero -> {
            JsonNode response = findComicsCharactersById(hero.getId(), 0, limit);
            if (Objects.nonNull(response) && !response.isEmpty()) {
                // Cargo la primer pagina
                populateDB(response.get("data").get("results"));

                // Obtengo la cantidad total de resultados.
                int resultCount = response.get("data").get("total").asInt();

                // Si hay mas resultados.
                if (resultCount > limit) {
                    int offset = limit;

                    // Itero hasta no encontrar resultados incrementando el offset (posicion inicial).
                    while (!response.get("data").get("results").isEmpty()) {
                        // Incremento la posicion inicial
                        offset += limit;

                        response = findComicsCharactersById(hero.getId(), offset, limit);
                        populateDB(response.get("data").get("results"));
                    }
                }
            }
        });

        log.info("Horario de fin de la Sincronizacion: {}....", LocalDateTime.now());
    }

    private void populateDB(JsonNode comics) {
        comics.forEach(comic -> {
            String title = comic.get("title").textValue();

            // Si existe el comic en la base
            Comic comicDb = comicsRepository
                    .findByName(title)
                    .orElse(new Comic(title));

            // se insertan los personajes del comic al objeto de persistencia
            addHeroes(comic.get("characters").get("items"), comicDb);

            // se insertan los colaboradores del comic al objeto de persistencia
            addColaborators(comic.get("creators").get("items"), comicDb);

            // se guardan los datos en la bd
            comicsRepository.save(comicDb);
        });
    }

    /**
     * Puebla los datos de los heroes.
     *
     * @param heroes  Listado de personajes en formato json.
     * @param comicDb Comic a modificar.
     */
    private void addHeroes(JsonNode heroes, Comic comicDb) {
        log.debug("listado de personajes a procesar {}", heroes.toString());

        heroes.forEach(hero -> {
            String heroName = hero.get("name").textValue();

            log.debug("procesando personaje {}, en el comic {}", heroName, comicDb.getName());
            Optional<Hero> heroOpt = heroesRepository.findByFullName(heroName);
            // si no existe en la bd se crea la nueva entidad
            comicDb.addHero(heroOpt.orElse(new Hero(heroName)));
        });
    }

    /**
     * Procesamiento de los datos de los colaboradores e insert en la DB.
     *
     * @param creators Listado de creators en formato json.
     * @param comicDb  Comic a modificar.
     */
    private void addColaborators(JsonNode creators, Comic comicDb) {
        log.debug("listado de colaboradores a procesar {}", creators.toString());

        creators.forEach(creator -> {
            String name = creator.get("name").textValue();
            String role = creator.get("role").textValue();

            if (Arrays.stream(Constantes.ROLES).anyMatch(role::contains)) {
                log.debug("procesando colaborador {}, l rol {} en el comic {}", name, role, comicDb.getName());
                Optional<Colaborator> colaboratorOpt = colaboratorsRepository.findByNameAndRole(name, role);
                // si no existe en la bd se crea la nueva entidad
                comicDb.addColaborator(colaboratorOpt.orElse(new Colaborator(name, role)));
            }
        });
    }

    /**
     * Invoca al servicio de Marvel ../characters/{characterId}/comics.
     *
     * @return Devuelve todos los comics (en formato json) en los que participo un personaje en particular.
     */
    private JsonNode findComicsCharactersById(Long heroId, Integer offset, Integer limit) {
        log.debug("findComicsCharactersById()");

        long timestamp = Instant.now().toEpochMilli();
        String md5Hash = DigestUtils.md5DigestAsHex(String.format("%s%s%s", timestamp, privateKey, publicKey).getBytes());

        String url = UriComponentsBuilder.newInstance()
                .scheme("https").host(marvelHost)
                .path("/characters/{character}/comics")
                .query("ts={ts}")
                .query("apikey={apikey}")
                .query("hash={hash}")
                .query("limit={limit}")
                .query("offset={offset}")
                .buildAndExpand(heroId, timestamp, publicKey, md5Hash, limit, offset)
                .toUriString();

        // Invoco al servicio
        log.debug("findComicsCharactersById() - Url= " + url);
        JsonNode retorno = marvelApiRestTemplate.getForObject(url, JsonNode.class);
        log.debug("findComicsCharactersById() - Respuesta= " + retorno);

        return retorno;
    }
}
