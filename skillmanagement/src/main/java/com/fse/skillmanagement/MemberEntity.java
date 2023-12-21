package com.fse.skillmanagement;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("MEMBER")
public class MemberEntity {

	@Id
	private int id;
	private String name;
	@MappedCollection(idColumn = "SKILL_ID")
	private Set<SkillEntity> skills = new HashSet<>();

	/**
	 * empty constructor needed for creating an object while working with the
	 * {@link JdbcProjectEntityRepository}
	 */
	public MemberEntity() {
	}

	public MemberEntity(Member member) {
		id = member.getMemberId().getId();
		name = member.getName();
	}

	public Member toDomain() {
		if (skills.isEmpty()) {
			return new Member(new MemberId(id), name);
		}
		Set<Skill> skillsToDomain = new HashSet<>();
		for (SkillEntity skill : skills) {
			skillsToDomain.add(skill.toDomain());
		}
		return new Member(new MemberId(id), name, skillsToDomain);
	}

	public int getId() {
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
		return skills;
	}

	public String toString() {
		String skillsString = "[";
		if (!skills.isEmpty()) {
			for (SkillEntity s : skills) {
				skillsString += s.toString();
			}
		} else {
			skillsString += "empty";
		}
		// deleting the last space
		skillsString = skillsString.substring(0, skillsString.length() - 1);
		skillsString += "]";
		return String.format("memberentity{@id=%1$s, name=%2$s, skillentities=%3$s}", id, name, skillsString);
	}
}
