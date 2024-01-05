package com.fse.projectmanagement.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fse.projectmanagement.infrastructure.entities.MemberEntity;

@Repository
public interface JdbcMemberEntityRepository 
	extends CrudRepository<MemberEntity, Integer> {

}
