package dev.bracers.rickandmorty.character;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import dev.bracers.rickandmorty.episode.EpisodeRepository;

@SpringBootTest
@ContextConfiguration(classes = {CharacterRepository.class, EpisodeRepository.class})
@EntityScan( basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"} )
@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

	@Autowired
	private CharacterRepository characterRepository;
	private CharacterService underTest;
	
	@BeforeEach
	void setUp() {
		underTest = new CharacterService(characterRepository);
		characterRepository.save(new Character(1L, "Rick Sanchez", null));
	}
	
	@Test
	void testGetCharacter() {
		try {
			underTest.getCharacter("Rick Sanchez");
		} catch (Exception e) {
			System.out.println("EXCEPTION");
		}
		verify(characterRepository).findCharacterByName("Rick Sanchez");
	}

}
