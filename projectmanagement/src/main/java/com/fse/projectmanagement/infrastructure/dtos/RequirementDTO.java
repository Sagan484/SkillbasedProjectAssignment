package com.fse.projectmanagement.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;

public class RequirementDTO extends DTO<Requirement>{

	String name;
	
	@JsonCreator
	public RequirementDTO(@JsonProperty("name") String name) {
		this.name = name;
	}
	
	@Override
	public Requirement toDomain() {
		return new Requirement(name);
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s", name);
	}
}
