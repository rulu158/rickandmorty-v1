package dev.bracers.rickandmorty;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import dev.bracers.rickandmorty.character.Character;
import dev.bracers.rickandmorty.character.CharacterRepository;
import dev.bracers.rickandmorty.consumer.characters.CharactersJSON;
import dev.bracers.rickandmorty.consumer.characters.CharactersResultJSON;
import dev.bracers.rickandmorty.consumer.episodes.EpisodesJSON;
import dev.bracers.rickandmorty.consumer.episodes.EpisodesResultJSON;
import dev.bracers.rickandmorty.episode.Episode;
import dev.bracers.rickandmorty.episode.EpisodeRepository;

@SpringBootApplication
public class RickandmortyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickandmortyApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean("getAllEpisodes")
	public CommandLineRunner getAllEpisodes(RestTemplate restTemplate, EpisodeRepository repository) throws Exception {
		return args -> {
			List<Episode> episodes = new ArrayList<Episode>();

			EpisodesJSON allEpisodes;
			String nextPage = "https://rickandmortyapi.com/api/episode";
			while (nextPage != null) {
				allEpisodes = restTemplate.getForObject(
						nextPage, EpisodesJSON.class);
				
				for (EpisodesResultJSON episodeResult : allEpisodes.results()) {
					Long id = episodeResult.id();
					String name = episodeResult.name();
					String airDate = episodeResult.airDate();
					
					episodes.add(new Episode(id, name, airDate));
				}
				
				nextPage = allEpisodes.info().next();
			}
			
			repository.saveAll(episodes);
		};
	}
	
	@Bean("getAllCharacters")
	@DependsOn("getAllEpisodes")
	public CommandLineRunner getAllCharacters(RestTemplate restTemplate, CharacterRepository repository) throws Exception {
		return args -> {
			List<Character> characters = new ArrayList<Character>();

			CharactersJSON allCharacters;
			String nextPage = "https://rickandmortyapi.com/api/character";
			while (nextPage != null) {
				allCharacters = restTemplate.getForObject(
						nextPage, CharactersJSON.class);
				
				for (CharactersResultJSON characterResult : allCharacters.results()) {
					Long id = characterResult.id();
					String name = characterResult.name();
					List<String> episodesUrl = characterResult.episodes();
					
					List<Episode> episodes = new ArrayList<Episode>();
					
					for (String episodeUrl : episodesUrl) {
						URI uri = new URI(episodeUrl);
						String path = uri.getPath();
						String idStr = path.substring(path.lastIndexOf('/') + 1);
						episodes.add(new Episode(Long.parseLong(idStr)));
					}
					
					characters.add(new Character(id, name, episodes));
				}
				
				nextPage = allCharacters.info().next();
			}
			
			repository.saveAll(characters);
		};
	}
}
