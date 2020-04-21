package com.project.pandemic.domain;

import java.util.ArrayList;
import java.util.List;

public class CountryUpdatesWrapper {
	
	private List<CountryUpdates> data;
	
	public  CountryUpdatesWrapper() {
		 //Initialise an array of country updates
        data = new ArrayList<>();		
	}
	
	public CountryUpdatesWrapper(List<CountryUpdates> data) {
		super();
		this.data = data;
	}

	public List<CountryUpdates> getData() {
		return data;
	}

	public void setData(List<CountryUpdates> data) {
		this.data = data;
	}
		
	
}
