package dev.bracers.rickandmorty.consumer.characters;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import dev.bracers.rickandmorty.character.Character;
import dev.bracers.rickandmorty.character.CharacterRepository;
import dev.bracers.rickandmorty.episode.Episode;

/*
 * Gets all Characters into the repository at the
 * startup of the application after all Episodes
 * have been retrieved 
 */
@Order(value = 2)
@Component
public class CharactersConsumer implements CommandLineRunner {

	private final RestTemplate restTemplate;
	
	private final CharacterRepository characterRepository;
	
	private final String charactersAPIEndpoint = "https://rickandmortyapi.com/api/character";

	@Autowired
	public CharactersConsumer(RestTemplate restTemplate, CharacterRepository characterRepository) {
		this.restTemplate = restTemplate;
		this.characterRepository = characterRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		List<Character> characters = new ArrayList<Character>();

		CharactersJSON allCharacters;
		String nextPage = charactersAPIEndpoint;
		
		// Iterate through all pages of the endpoint
		while (nextPage != null) {
			allCharacters = restTemplate.getForObject(
					nextPage, CharactersJSON.class);
			
			for (CharactersResultJSON characterResult : allCharacters.results()) {
				Long id = characterResult.id();
				String name = characterResult.name();
				List<String> episodesUrl = characterResult.episodes();
				
				List<Episode> episodes = new ArrayList<Episode>();
			
				// Associate an episode ID with the current Character for each episode
				// in Character's episodes
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
		
		characterRepository.saveAll(characters);
	}

}
