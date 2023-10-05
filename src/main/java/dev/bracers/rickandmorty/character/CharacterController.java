package dev.bracers.rickandmorty.character;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/search-character-appearance")
public class CharacterController {
	
	private final CharacterService characterService;
	
	@Autowired
	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}
	
	@GetMapping
	public ResponseEntity<List<Character>> findCharacter(@RequestParam("name") String name) {
		List<Character> characters;
		try {
			characters = characterService.getCharacter(name);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<Character>());
		}
		return ResponseEntity.ok().body(characters);
	}
}
