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

//@EnableAutoConfiguration
@ContextConfiguration(classes = CharacterTestConfig.class)
@EntityScan( basePackages = {"dev.bracers.rickandmorty.character", "dev.bracers.rickandmorty.episode"} )
@DataJpaTest
class CharacterRepositoryTest {

	@Autowired
	private CharacterRepository underTest;
	
	
	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}
	
	@Test
	void testFindCharacterByNameRickIsPresent() {
		// given
		
		// when
		Optional<List<Character>> expected = underTest.findCharacterByName("Rick Sanchez");
		
		// then
		assertThat(expected).isPresent();
		assertThat(expected.get()).hasSize(1);
	}

}
