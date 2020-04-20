package com.project.pandemic.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class CountryLatest {
	
	private Integer deaths;
	private Integer confirmed;
	private Integer recovered;
	private Integer critical;
	
	@Embedded
	private Calculated calculated;
	
	public CountryLatest() {
	
	}
	public CountryLatest(Integer deaths, Integer confirmed, Integer recovered, Integer critical,
			Calculated calculated) {
		super();
		this.deaths = deaths;
		this.confirmed = confirmed;
		this.recovered = recovered;
		this.critical = critical;
		this.calculated = calculated;
	}

	public Integer getDeaths() {
		return deaths;
	}

	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}

	public Integer getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}

	public Integer getRecovered() {
		return recovered;
	}

	public void setRecovered(Integer recovered) {
		this.recovered = recovered;
	}

	public Integer getCritical() {
		return critical;
	}

	public void setCritical(Integer critical) {
		this.critical = critical;
	}

	public Calculated getCalculated() {
		return calculated;
	}

	public void setCalculated(Calculated calculated) {
		this.calculated = calculated;
	}

	@Override
	public String toString() {
		return "CountryLatest [deaths=" + deaths + ", confirmed=" + confirmed + ", recovered=" + recovered
				+ ", critical=" + critical + ", calculated=" + calculated.toString() + "]";
	}
	
}
