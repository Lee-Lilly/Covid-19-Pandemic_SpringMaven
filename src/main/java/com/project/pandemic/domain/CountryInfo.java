package com.project.pandemic.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	@Column(name = "ISO2", unique = true)
	private String alpha2Code;
	
	@Column(name = "ISO3")
	private String alpha3Code;
	
	private String region;
	private String subregion;
	
	public CountryInfo() {
		
	}

	public CountryInfo(String name, String alpha2Code, String alpha3Code, String region, String subregion) {
		super();
		this.name = name;
		this.alpha2Code = alpha2Code;
		this.alpha3Code = alpha3Code;
		this.region = region;
		this.subregion = subregion;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}

	@Override
	public String toString() {
		return "CountryInfo [name=" + name + ", alpha2Code=" + alpha2Code + ", alpha3Code=" + alpha3Code + ", region="
				+ region + ", subregion=" + subregion + "]";
	}
	
	
	
	
}
