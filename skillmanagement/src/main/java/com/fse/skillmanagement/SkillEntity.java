package com.fse.skillmanagement;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "SKILL")
public class SkillEntity {

	@Id
	private int id;
	private String name;
	private String area;
	@MappedCollection(idColumn = "MEMBER_ID")
	private Set<MemberEntity> members = new HashSet<>();

	/**
	 * empty constructor needed for creating an object while working with the
	 * {@link JdbcMemberEntityRepository}
	 */
	public SkillEntity() {
	}

	public SkillEntity(Skill skill) {
		id = skill.getSkillId().getId();
		name = skill.getName();
		area = skill.getArea();
	}

	public Skill toDomain() {
		if (members.isEmpty()) {
			return new Skill(new SkillId(id), name, area);
		}
		Set<Member> membersToDomain = new HashSet<>();
		for (MemberEntity member : members) {
			membersToDomain.add(member.toDomain());
		}
		return new Skill(new SkillId(id), name, area, membersToDomain);
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

	public int getSkillId() {
		return id;
	}

	public String toString() {
		return String.format("skillentity{@id=%1$s, name=%2$s, area=%3$s} ", id, name, area);
	}
}
