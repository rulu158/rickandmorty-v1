package dev.bracers.rickandmorty.consumer.characters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CharactersInfoJSON(
		@JsonProperty("count") Integer count,
		@JsonProperty("pages") Integer pages,
		@JsonProperty("next") String next,
		@JsonProperty("prev") String prev) {
}
