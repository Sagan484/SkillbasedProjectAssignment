package com.fse.projectmanagement.infrastructure.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;
import com.fse.projectmanagement.infrastructure.repositories.JdbcProjectEntityRepository;

@Table("PROJECT")
public class ProjectEntity {

	@Id
	private Integer id;
	private String name;
	@MappedCollection(idColumn = "PROJECT_ID")
	private Set<MemberEntity> memberEntitites = new HashSet<>();
	@MappedCollection(idColumn = "PROJECT_ID")
	private Set<RequirementEntity> requirementEntities = new HashSet<>();
	
	/** empty constructor needed for creating an object while working with the {@link JdbcProjectEntityRepository} */
	public ProjectEntity() {}
	
	public ProjectEntity(Project project) {
		id = project.getProjectId().getId();
		name = project.getName();
		Set<Member> members = project.getMembers();
		if(!members.isEmpty()) {
			memberEntitites = members.stream()
		            .map(member -> new MemberEntity(member))
		            .collect(Collectors.toSet());
		}
		Set<Requirement> requirements = project.getRequirements();
		if(!requirements.isEmpty()) {
			requirementEntities = requirements.stream()
		            .map(requirement -> new RequirementEntity(requirement))
		            .collect(Collectors.toSet());
		}
	}
	
	public Project toDomain() {
		Set<Member> members = memberEntitites.stream()
	            .map(MemberEntity::toDomain)
	            .collect(Collectors.toSet());
		Set<Requirement> requirements = requirementEntities.stream()
	            .map(RequirementEntity::toDomain)
	            .collect(Collectors.toSet());
		return new Project(new ProjectId(id), name, members, requirements);
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<MemberEntity> getMembers() {
		return memberEntitites;
	}
	
	public Set<RequirementEntity> getRequirements() {
		return requirementEntities;
	}
}
