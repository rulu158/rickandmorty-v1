package dev.bracers.rickandmorty.character;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.bracers.rickandmorty.episode.Episode;
import dev.bracers.rickandmorty.episode.EpisodeRepository;

@EnableAutoConfiguration
//@ContextConfiguration(classes = {CharacterRepository.class, EpisodeRepository.class})
@ContextConfiguration(classes = CharacterConfig.class)
@EntityScan( basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"} )
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
class CharacterRepositoryTest {

	@Autowired
	private CharacterRepository underTest;
	@Autowired
	private EpisodeRepository episodeRepository;
	
	
	
//	@AfterEach
//	void tearDown() {
//		underTest.deleteAll();
//	}
	
	@Test
	void testFindCharacterByName() {
		// given
		List<Episode> episodes = Arrays.asList(new Episode(1L, "Episode 1", "September 4, 2013"),
				new Episode(2L, "Episode 2", "September 14, 2013"), new Episode(3L, "Episode 3", "October 4, 2013"));
		episodeRepository.saveAll(episodes);
		List<Character> characters = Arrays.asList(
				new Character(
						1L,
						"Rick Sanchez",
						Arrays.asList(
								new Episode(1L, "Episode 1", "September 4, 2013"),
								new Episode(2L, "Episode 2", "September 14, 2013"),
								new Episode(3L, "Episode 3", "October 4, 2013")
								)
				),
				new Character(
						2L,
						"Morty Smith",
						Arrays.asList(
								new Episode(1L, "Episode 1", "September 4, 2013"),
								new Episode(3L, "Episode 3", "October 4, 2013")
						)
				)
		);
		underTest.saveAll(characters);
		
		
		// when
		Optional<List<Character>> expected = underTest.findCharacterByName("Rick Sanchez");
		
		// then
		assertThat(expected).isPresent();
		assertThat(expected.get()).hasSize(1);
	}

}
