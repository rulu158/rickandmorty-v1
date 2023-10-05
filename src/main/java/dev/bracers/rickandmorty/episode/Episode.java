package dev.bracers.rickandmorty.episode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Episode {
	@Id
	@JsonIgnore
	private long id;
	
	private String name;
	
	@JsonIgnore
	private String airDate;
	
	public Episode() {
		
	}
	
	public Episode(Long id) {
		this.id = id;
	}
	
	public Episode(Long id, String name, String airDate) {
		this.id = id;
		this.name = name;
		this.airDate = airDate;
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

	public String getAirDate() {
		return airDate;
	}

	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}
	
	
}
