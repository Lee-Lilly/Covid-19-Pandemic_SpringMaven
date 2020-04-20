package com.project.pandemic.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryTimeline {	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="country", unique = true)
	private String name;
	
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
	
	
}
