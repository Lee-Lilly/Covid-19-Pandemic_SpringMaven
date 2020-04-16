package com.project.pandemic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryData, Long> {
	
	List<CountryData> findByOrderByCountry();

}
