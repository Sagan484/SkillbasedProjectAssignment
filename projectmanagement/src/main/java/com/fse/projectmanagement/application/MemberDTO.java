package com.fse.projectmanagement.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;

public class MemberDTO extends DTO<Member> {
	
	Integer id;
	String name;
	
	@JsonCreator
	public MemberDTO(@JsonProperty("id") Integer id, @JsonProperty("name") String name) {
		this.name = name;
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public Member toDomain() {
		return new Member(new MemberId(id), name);
	}
	
	@Override
	public String toString() {
		return String.format("member ID: %s, name: %s", id, name);
	}
}
