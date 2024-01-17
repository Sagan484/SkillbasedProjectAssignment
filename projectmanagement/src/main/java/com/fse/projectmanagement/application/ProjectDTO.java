package com.fse.projectmanagement.application;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;

public class ProjectDTO extends DTO<Project> {
		
	private Integer id;
	private String name;
	private Set<MemberDTO> memberDtos;
	private Set<RequirementDTO> requirementDtos;
	private DTOtoDomainMapper dtoToDomainMapper;
	
	@JsonCreator
	public ProjectDTO(@JsonProperty("id") Integer id, @JsonProperty("name") String name, @JsonProperty("members") Set<MemberDTO> memberDtos, @JsonProperty("requirements") Set<RequirementDTO> requirementDTOs) {
		this.id = id;
		this.name = name;
		this.memberDtos = memberDtos;
		this.requirementDtos = requirementDTOs;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<MemberDTO> getMembers() {
		return memberDtos;
	}
	
	public Set<RequirementDTO> getRequirements() {
		return requirementDtos;
	}
	
	@Override
	public Project toDomain() {		
		Set<Member> members = dtoToDomainMapper.map(memberDtos);
		Set<Requirement> requirements = dtoToDomainMapper.map(requirementDtos);
		return new Project(new ProjectId(id), name, members, requirements);
	}
	
	@Override
	public String toString() {
		return String.format("ProjektID: %s, Name: %s, Members: %s, Requirements: %s", id, name, memberDtos.toString(), requirementDtos.toString());
	}
}
