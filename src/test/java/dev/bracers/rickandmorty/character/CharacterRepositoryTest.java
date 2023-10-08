package dev.bracers.rickandmorty.character;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = CharacterTestConfig.class)
@EntityScan(basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"})
class CharacterRepositoryTest {

	@Autowired
	private CharacterRepository underTest;
	
	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}
	
	@Test
	void testFindCharacterByNameRickIsPresent() {		
		List<Character> expected = underTest.findCharacterByName("Rick Sanchez");
		
		assertThat(expected.size()).isGreaterThan(0);
	}
	
	@Test
	void testFindCharacterByNameMortyIsPresentInAllEpisodes() {
		List<Character> expected = underTest.findCharacterByName("Morty Smith");
		
		assertThat(expected).hasSize(1);
		assertThat(expected.get(0).getEpisodes()).hasSize(6);
	}
	
	@Test
	void testFindCharacterByNameAbadangoIsPresentInOneEpisode() {
		List<Character> expected = underTest.findCharacterByName("Abadango Cluster Princess");
		
		assertThat(expected).hasSize(1);
	}
	
	@Test
	void testFindCharacterByNameOriginalRickFirstAppearanceIsCorrect() {
		List<Character> expected = underTest.findCharacterByName("Rick Sanchez");
		
		assertThat(expected.size()).isGreaterThan(0);
		assertThat(expected.get(0).getFirstAppearance()).isEqualTo("September 4, 2013");
	}
	
	@Test
	void testFindCharacterByNameRaulIsNotPresent() {
		List<Character> expected = underTest.findCharacterByName("Raul Garcia");
		
		assertThat(expected).hasSize(0);
	}

}
