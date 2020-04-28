package com.project.pandemic.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TimelineWrapper {
	
	private List<Timeline> data;
	 
    public TimelineWrapper() { //Initialise an array of timeline list
        data = new ArrayList<>();
    }
    
	public List<Timeline> getData() {
		return data;
	}

	public void setData(List<Timeline> data) {
		this.data = data;
	}

}
