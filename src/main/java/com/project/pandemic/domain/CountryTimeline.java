package com.project.pandemic.domain;

import java.util.List;

import javax.persistence.Embedded;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryTimeline {	
	
	private String name;
	private String code;
	private Integer population;
	
	@Embedded
	private CountryLatest latest_data;
	
	@Embedded
	private List<Timeline> timeline;
	
	public CountryTimeline() {
		
	}
	
	public CountryTimeline(String name, List<Timeline> timeline) {
		super();
		this.name = name;
		this.timeline = timeline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Timeline> getTimeline() {
		return timeline;
	}

	public void setTimeline(List<Timeline> timeline) {
		this.timeline = timeline;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}
	
	public CountryLatest getLatest_data() {
		return latest_data;
	}

	public void setLatest_data(CountryLatest latest_data) {
		this.latest_data = latest_data;
	}
	
}
