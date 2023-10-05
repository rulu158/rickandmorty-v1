package dev.bracers.rickandmorty.character;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>{
	Optional<List<Character>> findCharacterByName(String name);
}
