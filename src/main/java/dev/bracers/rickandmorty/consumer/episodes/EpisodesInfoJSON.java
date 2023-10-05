package dev.bracers.rickandmorty.consumer.episodes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodesInfoJSON(
		@JsonProperty("count") Integer count,
		@JsonProperty("pages") Integer pages,
		@JsonProperty("next") String next,
		@JsonProperty("prev") String prev) {
}
