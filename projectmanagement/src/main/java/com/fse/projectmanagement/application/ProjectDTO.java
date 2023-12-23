package com.fse.projectmanagement.application;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;

public class ProjectDTO {
		
	Integer id;
	String name;
	Set<MemberDTO> membersDto;
	
	@JsonCreator
	public ProjectDTO(@JsonProperty("id") Integer id, @JsonProperty("name") String name, @JsonProperty("members") Set<MemberDTO> membersDto) {
		this.id = id;
		this.name = name;
		this.membersDto = membersDto;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<MemberDTO> getMembers() {
		return membersDto;
	}
	
	public Project toDomain() {
		Set<Member> members = membersDto.stream()
				.map(m -> m.toDomain())
				.collect(Collectors.toSet());
		return new Project(new ProjectId(id), name, members);
	}
	
	public String toString() {
		return String.format("ProjektID: %1$s, Name: %2$s", id, name, membersDto.stream());
	}
}
