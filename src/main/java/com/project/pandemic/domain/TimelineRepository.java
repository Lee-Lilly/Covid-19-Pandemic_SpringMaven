package com.project.pandemic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TimelineRepository extends CrudRepository<Timeline, Long> {
	List<Timeline> findByOrderByDate();
}
