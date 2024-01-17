package com.fse.projectmanagement.infrastructure.entities;

import org.springframework.data.relational.core.mapping.Table;

import com.fse.projectmanagement.domain.aggregates.project.Requirement;
import com.fse.projectmanagement.infrastructure.repositories.JdbcProjectEntityRepository;

@Table("REQUIREMENT")
public class RequirementEntity {

	private String name;
	
	/** empty constructor needed for creating an object while working with the {@link JdbcProjectEntityRepository} */
	public RequirementEntity() {}
	
	public RequirementEntity(Requirement requirement) {
		name = requirement.getName();
	}
	
	public Requirement toDomain() {
		return new Requirement(name);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s", name);
	}
}
