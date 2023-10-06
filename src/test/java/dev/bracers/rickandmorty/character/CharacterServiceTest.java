package dev.bracers.rickandmorty.character;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {CharacterTestConfig.class})
class CharacterServiceTest {

	@Autowired
	private CharacterRepository characterRepository;
	
	@Mock
	private CharacterRepository mockRepository;
	
	private CharacterService underTest;
	
	@BeforeEach
	void setUp() {
		underTest = new CharacterService(characterRepository);
	}
	
	@Test
	void testGetCharacterRickIsThere() {
		assertDoesNotThrow(() -> underTest.getCharacter("Rick Sanchez"));
	}
	
	@Test
	void testGetCharacterRaulIsNotThere() {
		assertThatThrownBy(() -> underTest.getCharacter("Raul Garcia"))
				.hasMessageContaining("No character with that name.");
	}
	
	@Test
	void testGetCharacterRepositoryIsBeingCalled() {
		CharacterService underTest = new CharacterService(mockRepository);
		
		// any value for getCharacter will throw as we are using a mock
		assertThatThrownBy(() -> underTest.getCharacter("Test Test"))
				.hasMessageContaining("No character with that name.");

		verify(mockRepository).findCharacterByName("Test Test");
	}

}
