package com.fse.projectmanagement.infrastructure.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;
import com.fse.projectmanagement.infrastructure.repositories.JdbcProjectEntityRepository;

@Table("MEMBER")
public class MemberEntity {

	@Id
	private Integer id;
	private String name;
	
	/** empty constructor needed for creating an object while working with the {@link JdbcProjectEntityRepository} */
	public MemberEntity() {}
	
	public MemberEntity(Member member) {
		id = member.getMemberId().getId();
		name = member.getName();
	}

	public Member toDomain() {
		return new Member(new MemberId(id), name);
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
