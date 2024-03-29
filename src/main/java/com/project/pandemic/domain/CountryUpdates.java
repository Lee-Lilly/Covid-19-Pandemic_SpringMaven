package com.project.pandemic.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryUpdates {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "country")
	private String name;
	
	@Column(unique = true)
	private String code;
	
	private Integer population;
	private Date updated_at;
	
	@Embedded
	private CountryLatest latest_data;
	
	@Embedded 
	private TodayNews today;
	
	public CountryUpdates() {
		
	}
	public CountryUpdates(String name, String code, Integer population, Date updated_at, CountryLatest latest_data, TodayNews today) {
		super();
		this.name = name;
		this.code = code;
		this.population = population;
		this.updated_at = updated_at;
		this.latest_data = latest_data;
		this.today = today;
	}
		
	public TodayNews getToday() {
		return today;
	}
	public void setToday(TodayNews today) {
		this.today = today;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer population) {
		this.population = population;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	public CountryLatest getLatest_data() {
		return latest_data;
	}
	public void setLatest_data(CountryLatest latest_data) {
		this.latest_data = latest_data;
	}
	@Override
	public String toString() {
		return "CountryUpdates [name=" + name + ", code=" + code + ", population=" + population + ", updated_at="
				+ updated_at + ", latest_data=" + latest_data.toString() + "]";
	}
		
}
