package com.fse.skillmanagement.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fse.skillmanagement.infrastructure.entities.MemberEntity;

@Repository
public interface JdbcMemberEntityRepository 
	extends CrudRepository<MemberEntity, Integer> {
}
