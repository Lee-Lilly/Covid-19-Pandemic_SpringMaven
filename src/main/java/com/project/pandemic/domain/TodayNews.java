package com.project.pandemic.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TodayNews {

	@Column(name = "deaths_tody")
	private int deaths;
	
	@Column(name = "new_confirmed")
	private int confirmed;
	
	public TodayNews() {
		
	}
	
	public TodayNews(int deaths, int confirmed) {
		super();
		this.deaths = deaths;
		this.confirmed = confirmed;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	
}
