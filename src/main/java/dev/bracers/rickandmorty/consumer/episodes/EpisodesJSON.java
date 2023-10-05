package dev.bracers.rickandmorty.consumer.episodes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesJSON(EpisodesInfoJSON info, List<EpisodesResultJSON> results) {
}
