package com.fse.membermanagement.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fse.membermanagement.domain.aggregates.member.Skill;

public class SkillDTO {
	private String name;
	private String area;
	
	@JsonCreator
	public SkillDTO (@JsonProperty("name") String name, @JsonProperty("area") String area) {
		this.name = name;
		this.area = area;
	}
	
	public String getName() {
		return name;
	}
	
	public String getArea() {
		return area;
	}
	
	public Skill toDomain() {
		return new Skill(name, area);
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s, Area: %s", name, area);
	}
}