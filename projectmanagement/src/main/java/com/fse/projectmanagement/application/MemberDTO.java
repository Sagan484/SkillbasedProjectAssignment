package com.fse.projectmanagement.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;

public class MemberDTO {
	
	Integer id;
	String name;
	
	@JsonCreator
	public MemberDTO(Integer id, String name) {
		this.name = name;
		this.id = id;
	}
	
	public Member toDomain() {
		return new Member(new MemberId(id), name);
	}
}
