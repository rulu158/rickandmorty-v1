package dev.bracers.rickandmorty.consumer.characters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CharactersResultJSON(
		@JsonProperty("id") Long id,
		@JsonProperty("name") String name,
		@JsonProperty("episode") List<String> episodes) {
}
