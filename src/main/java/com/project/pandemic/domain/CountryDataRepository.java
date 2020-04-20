package com.project.pandemic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CountryDataRepository extends CrudRepository<CountryData, Long> {
	
	List<CountryData> findByOrderByCountry();

}
