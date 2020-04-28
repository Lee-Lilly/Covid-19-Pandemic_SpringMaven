package com.project.pandemic.domain;

import java.util.List;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CountryDataRepository extends CrudRepository<CountryData, Long> {
	
	List<CountryData> findByOrderByCountry();
	
	List<CountryData> findByContinent(String continent);

}
