package com.project.pandemic.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Calculated {
	
	private float death_rate;
	private float recovery_rate;
	private float recovered_vs_death_ratio;
	private float cases_per_million_population;
	
	public Calculated() {
		
	}
	
	public Calculated(float death_rate, float recovery_rate, float recovered_vs_death_ratio,
			float cases_per_million_population) {
		super();
		this.death_rate = death_rate;
		this.recovery_rate = recovery_rate;
		this.recovered_vs_death_ratio = recovered_vs_death_ratio;
		this.cases_per_million_population = cases_per_million_population;
	}
	
	public float getDeath_rate() {
		return death_rate;
	}
	public void setDeath_rate(float death_rate) {
		this.death_rate = death_rate;
	}
	public float getRecovery_rate() {
		return recovery_rate;
	}
	public void setRecovery_rate(float recovery_rate) {
		this.recovery_rate = recovery_rate;
	}
	public float getRecovered_vs_death_ratio() {
		return recovered_vs_death_ratio;
	}
	public void setRecovered_vs_death_ratio(float recovered_vs_death_ratio) {
		this.recovered_vs_death_ratio = recovered_vs_death_ratio;
	}
	public float getCases_per_million_population() {
		return cases_per_million_population;
	}
	public void setCases_per_million_population(float cases_per_million_population) {
		this.cases_per_million_population = cases_per_million_population;
	}

	@Override
	public String toString() {
		return "Calculated [death_rate=" + death_rate + ", recovery_rate=" + recovery_rate
				+ ", recovered_vs_death_ratio=" + recovered_vs_death_ratio + ", cases_per_million_population="
				+ cases_per_million_population + "]";
	}
		
}
