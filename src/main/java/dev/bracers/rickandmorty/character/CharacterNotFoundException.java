package dev.bracers.rickandmorty.character;

public class CharacterNotFoundException extends Exception { 
    private static final long serialVersionUID = -3421219955747124235L;

	public CharacterNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}