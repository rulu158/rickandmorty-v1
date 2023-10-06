package dev.bracers.rickandmorty.character;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

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
		Optional<List<Character>> expected = underTest.findCharacterByName("Rick Sanchez");
		
		assertThat(expected).isPresent();
		assertThat(expected.get().size()).isGreaterThan(0);
	}
	
	@Test
	void testFindCharacterByNameMortyIsPresentInAllEpisodes() {
		Optional<List<Character>> expected = underTest.findCharacterByName("Morty Smith");
		
		assertThat(expected).isPresent();
		assertThat(expected.get()).hasSize(1);
		assertThat(expected.get().get(0).getEpisodes()).hasSize(6);
	}
	
	@Test
	void testFindCharacterByNameAbadangoIsPresentInOneEpisode() {
		Optional<List<Character>> expected = underTest.findCharacterByName("Abadango Cluster Princess");
		
		assertThat(expected).isPresent();
		assertThat(expected.get()).hasSize(1);
	}
	
	@Test
	void testFindCharacterByNameOriginalRickFirstAppearanceIsCorrect() {
		Optional<List<Character>> expected = underTest.findCharacterByName("Rick Sanchez");
		
		assertThat(expected).isPresent();
		assertThat(expected.get().size()).isGreaterThan(0);
		assertThat(expected.get().get(0).getFirstAppearance()).isEqualTo("September 4, 2013");
	}
	
	@Test
	void testFindCharacterByNameRaulIsNotPresent() {
		Optional<List<Character>> expected = underTest.findCharacterByName("Raul Garcia");
		
		assertThat(expected).isPresent();
		assertThat(expected.get()).hasSize(0);
	}

}
