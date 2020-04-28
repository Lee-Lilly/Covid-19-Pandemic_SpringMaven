package com.project.pandemic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CountryUpdatesRepository extends CrudRepository<CountryUpdates, Long>{
	List<CountryUpdates> findByOrderByName();
	List<CountryUpdates> findByCode(String code);

}
