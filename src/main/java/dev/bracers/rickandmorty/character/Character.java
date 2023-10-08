package dev.bracers.rickandmorty.character;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.bracers.rickandmorty.episode.Episode;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table
public class Character {
	@Id
	@JsonIgnore
	private long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("episodes")
	@ManyToMany
	private List<Episode> episodes;
	
	@JsonProperty("first_appearance")
	@Transient
	private String firstAppearance;
	
	public Character() {
		
	}
	
	public Character(Long id, String name, List<Episode> episodes) {
		this.id = id;
		this.name = name;
		this.episodes = episodes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public String getFirstAppearance() {
		return this.getEpisodes().get(0).getAirDate();
	}

	public void setFirstAppearance(String firstAppearance) {
		this.firstAppearance = firstAppearance;
	}
	
	
}
