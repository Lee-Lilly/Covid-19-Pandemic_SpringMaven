package com.project.pandemic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryTimelineWrapper {
	
	private CountryTimeline data;

	public CountryTimelineWrapper() {
		
	}
	public CountryTimelineWrapper(CountryTimeline data) {
		super();
		this.data = data;
	}

	public CountryTimeline getData() {
		return data;
	}

	public void setData(CountryTimeline data) {
		this.data = data;
	}
	

}
