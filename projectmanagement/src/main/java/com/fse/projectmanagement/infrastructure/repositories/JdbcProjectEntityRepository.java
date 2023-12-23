package com.fse.projectmanagement.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fse.projectmanagement.infrastructure.entities.ProjectEntity;

@Repository
public interface JdbcProjectEntityRepository 
	extends CrudRepository<ProjectEntity, Integer> {

}
