package com.project.pandemic.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CountryUpdatesRepository extends CrudRepository<CountryUpdates, Long>{
	
	List<CountryUpdates> findByOrderByName();
	
	CountryUpdates findByCode(String code);
	
	CountryUpdates findByName(String name);
	
	@Query(value="select * from COUNTRY_UPDATES c where c.country like %:keyword%", nativeQuery=true)
	List<CountryUpdates> findByKeyword(@Param("keyword") String keyword);
}
