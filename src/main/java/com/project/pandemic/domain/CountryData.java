package com.project.pandemic.domain;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryData {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String country;
	private String continent;	
	private Integer cases;
	private Integer deaths;
	private Integer critical;
	private Integer recovered;
	private Integer active;
	private Integer tests;
	
	@Transient
	private Long updated;
	
	private Date date;
	
	public CountryData() {
		
	}
	
	public CountryData(String country, String continent, Integer cases, Integer deaths, Integer critical, Integer recovered, Integer active, Integer tests, Date date) {
		this.country = country;
		this.continent = continent;
		this.cases = cases;
		this.deaths = deaths;
		this.critical = critical;
		this.recovered = recovered;
		this.active = active;
		this.tests = tests;
		this.date = date;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
		
	public Integer getCases() {
		return cases;
	}
	
	public void setCases(Integer cases) {
		this.cases = cases;
	}
	
	public Integer getDeaths() {
		return deaths;
	}
	
	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}
	
	public Integer getCritical() {
		return critical;
	}
	
	public void setCritical(Integer critical) {
		this.critical = critical;
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
	
	public Integer getTests() {
		return tests;
	}

	public void setTests(Integer tests) {
		this.tests = tests;
	}
	
	public Long getUpdated() {
		return updated;
	}
	
	public void setUpdated(Long updated) {
		this.updated = updated;
	}
	
	public Date getDate() {
		return date = Date.from(Instant.ofEpochMilli(updated));
	}

	public void setDate(Long updated) {
		this.date = Date.from(Instant.ofEpochMilli(updated));
	}
			
	@Override
	  public String toString() {
		
	    return "PandemicData{" +
	        "country='" + country + ' ' +
	        ", continent = " + continent + ' ' +
	        ", cases=" + cases + ' ' + 
	        ", deaths= " + deaths + ' ' +
	        ", critical = " + critical + ' ' +
	        ", recovered = " + recovered + ' ' +
	        ", active = " + active + ' ' +
	        ", tests = " + tests + ' ' +
	        ", date = " + date +
	        '}';
	  }

}
