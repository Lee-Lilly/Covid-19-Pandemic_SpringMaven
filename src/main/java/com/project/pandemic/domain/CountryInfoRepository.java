package com.project.pandemic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CountryInfoRepository extends CrudRepository<CountryInfo, Long> {
	
	List<CountryInfo> findByOrderByName();
}
