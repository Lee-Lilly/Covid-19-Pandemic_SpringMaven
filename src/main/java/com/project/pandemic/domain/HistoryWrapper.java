package com.project.pandemic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryWrapper {
	
	private CountryHistory data;

	public HistoryWrapper() {
		
	}
	public HistoryWrapper(CountryHistory data) {
		super();
		this.data = data;
	}

	public CountryHistory getData() {
		return data;
	}

	public void setData(CountryHistory data) {
		this.data = data;
	}
	

}
