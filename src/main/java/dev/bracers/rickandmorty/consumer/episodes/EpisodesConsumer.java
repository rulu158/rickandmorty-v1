package dev.bracers.rickandmorty.consumer.episodes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import dev.bracers.rickandmorty.episode.Episode;
import dev.bracers.rickandmorty.episode.EpisodeRepository;

/*
 * Gets all Episodes into the repository at the
 * startup of the application 
 */
@Order(value = 1)
@Component
public class EpisodesConsumer implements CommandLineRunner {

	private final RestTemplate restTemplate;
	
	private final EpisodeRepository episodeRepository;
	
	private final String episodesAPIEndpoint = "https://rickandmortyapi.com/api/episode";

	@Autowired
	public EpisodesConsumer(RestTemplate restTemplate, EpisodeRepository episodeRepository) {
		this.restTemplate = restTemplate;
		this.episodeRepository = episodeRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		List<Episode> episodes = new ArrayList<Episode>();

		EpisodesJSON allEpisodes;
		String nextPage = episodesAPIEndpoint;
		
		// Iterate through all pages of the endpoint 
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
		
		episodeRepository.saveAll(episodes);
	}

}
