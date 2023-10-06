package dev.bracers.rickandmorty.character;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
	
	private final CharacterRepository characterRepository;
	
	@Autowired
	public CharacterService(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public List<Character> getCharacter(String name) {
		Optional<List<Character>> characterOptional = characterRepository.findCharacterByName(name);
		if (!characterOptional.isPresent() || (characterOptional.isPresent() && characterOptional.get().isEmpty())) {
			throw new IllegalStateException("No character with that name.");
		}
		return characterOptional.get();
	}

}
