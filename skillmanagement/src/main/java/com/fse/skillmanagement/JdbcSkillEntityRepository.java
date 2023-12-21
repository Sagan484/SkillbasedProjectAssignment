package com.fse.skillmanagement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdbcSkillEntityRepository 
	extends CrudRepository<SkillEntity, Integer> {
}
