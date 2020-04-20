package com.project.pandemic.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfo {
	@Column	
	private String iso2;
	@Column
	private String iso3;
	
	public CountryInfo() {
		
	}

	public CountryInfo(String iso2, String iso3) {
		super();
		this.iso2 = iso2;
		this.iso3 = iso3;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	@Override
	public String toString() {
		return "CountryInfo [iso2=" + iso2 + ", iso3=" + iso3 + "]";
	}
	
}
