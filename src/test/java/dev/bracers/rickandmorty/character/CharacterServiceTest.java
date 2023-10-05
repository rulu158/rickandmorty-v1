package dev.bracers.rickandmorty.character;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {CharacterTestConfig.class})
//@EntityScan( basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"} )
//@ComponentScan( basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"} )
//@EnableJpaRepositories(basePackageClasses = {EpisodeRepository.class, CharacterRepository.class})

//@EntityScan(basePackageClasses = {Episode.class, Character.class})
//@ExtendWith(MockitoExtension.class)
//@DataJpaTest
class CharacterServiceTest {

	@Autowired
	private CharacterRepository characterRepository;
	
	private CharacterService underTest;
	
	@BeforeEach
	void setUp() {
		underTest = new CharacterService(characterRepository);
	}
	
	@Test
	void testGetCharacterRickIsThere() {
		assertDoesNotThrow(() -> underTest.getCharacter("Rick Sanchez"));
	}

}
