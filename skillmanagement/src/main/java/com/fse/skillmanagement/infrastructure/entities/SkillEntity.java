package com.fse.skillmanagement.infrastructure.entities;

import org.springframework.data.relational.core.mapping.Table;

import com.fse.skillmanagement.domain.aggregates.member.Skill;
import com.fse.skillmanagement.infrastructure.repositories.JdbcMemberEntityRepository;

@Table("SKILL")
public class SkillEntity {

	private String name;
	private String area;

	/**
	 * empty constructor needed for creating an object while working with the
	 * {@link JdbcMemberEntityRepository}
	 */
	public SkillEntity() {
	}

	public SkillEntity(Skill skill) {
		name = skill.getName();
		area = skill.getArea();
	}

	public Skill toDomain() {
		return new Skill(name, area);
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s, Area: %s", name, area);
	}

	public String getArea() {
		return area;
	}
}
