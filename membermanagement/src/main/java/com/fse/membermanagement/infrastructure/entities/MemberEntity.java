package com.fse.membermanagement.infrastructure.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.MemberId;
import com.fse.membermanagement.domain.aggregates.member.Skill;
import com.fse.membermanagement.infrastructure.repositories.JdbcMemberEntityRepository;

@Table("MEMBER")
public class MemberEntity {

	@Id
	private Integer id;
	private String name;
	@MappedCollection(idColumn = "MEMBER_ID")
	private Set<SkillEntity> skillEntities = new HashSet<>();

	/**
	 * empty constructor needed for creating an object while working with the
	 * {@link JdbcMemberEntityRepository}
	 */
	public MemberEntity() {
	}

	public MemberEntity(Member member) {
		id = member.getMemberId().getId();
		name = member.getName();
		Set<Skill> skills = member.getSkills();
		if(!skills.isEmpty()) {
			skillEntities = skills.stream()
		            .map(skill -> new SkillEntity(skill))
		            .collect(Collectors.toSet());
		}
	}

	public Member toDomain() {
		Set<Skill> skills = skillEntities.stream()
	            .map(SkillEntity::toDomain)
	            .collect(Collectors.toSet());
		return new Member(new MemberId(id), name, skills);
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SkillEntity> getSkills() {
		return skillEntities;
	}
	
	@Override
	public String toString() {
		return String.format("member ID: %s, name: %s, skills: %s", id, name, skillEntities);
	}
}
