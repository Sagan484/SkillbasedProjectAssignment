package com.fse.membermanagement.infrastructure.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.MemberId;
import com.fse.membermanagement.domain.aggregates.member.Skill;

public class MemberDTO {
	
	Integer id;
	String name;
	Set<SkillDTO> skillDTOs;
	
	@JsonCreator
	public MemberDTO(@JsonProperty("id") Integer id, @JsonProperty("name") String name, @JsonProperty("skills") Set<SkillDTO> skillDTOs) {
		this.id = id;
		this.name = name;
		this.skillDTOs = skillDTOs;
	}
	
	public MemberDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<SkillDTO> getSkills() {
		return skillDTOs;
	}
	
	public Member toDomain() {
		Set<Skill> skills = skillDTOs.stream()
				.map(s -> s.toDomain())
				.collect(Collectors.toSet());
		return new Member(new MemberId(id), name, skills);
	}
	
	@Override
	public String toString() {
		return String.format("member ID: %s, name: %s, skills: %s", id, name, skillDTOs);
	}
}
