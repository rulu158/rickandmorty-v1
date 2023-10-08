package dev.bracers.rickandmorty.character;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
	
	private final CharacterRepository characterRepository;
	
	@Autowired
	public CharacterService(CharacterRepository characterRepository) {
		this.characterRepository = characterRepository;
	}

	public List<Character> getCharacter(String name) throws CharacterNotFoundException {
		List<Character> character = characterRepository.findCharacterByName(name);
		if (character.isEmpty()) {
			throw new CharacterNotFoundException("No character with that name.");
		}
		return character;
	}

}
