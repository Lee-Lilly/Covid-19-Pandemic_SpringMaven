package com.project.pandemic.domain;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Timeline {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Integer confirmed;
	private Integer deaths;
	private Integer recovered;
	private Integer active;
	private Date date;
	
	public Timeline() {
		
	}
	
	public Timeline(Integer confirmed, Integer deaths, Integer recovered, Integer active, Date date) {
		super();
		this.confirmed = confirmed;
		this.deaths = deaths;
		this.recovered = recovered;
		this.active = active;
		this.date = date;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public Integer getDeaths() {
		return deaths;
	}

	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}

	public Integer getRecovered() {
		return recovered;
	}

	public void setRecovered(Integer recovered) {
		this.recovered = recovered;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Timeline [confirmed=" + confirmed + ", deaths=" + deaths + ", recovered=" + recovered + ", active="
				+ active + ", date=" + date + "]";
	}

	
	
	

	

}
