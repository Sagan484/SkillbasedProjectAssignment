package com.fse.skillmanagement;

import java.util.HashSet;
import java.util.Set;

public class Member {

	private MemberId id;
	private String name;
	private Set<Skill> skills = new HashSet<>();

	public Member(MemberId id, String name, Set<Skill> skills) {
		this.id = id;
		this.name = name;
		this.skills = skills;
	}

	public Member(MemberId id, String name) {
		this.id = id;
		this.name = name;
	}

	public MemberEntity toDatabase() {
		return new MemberEntity(this);
	}

	public MemberId getMemberId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public String toString() {
		String skillsString = "[";
		if (skills != null) {
			for (Skill s : skills) {
				skillsString += s.toString();
			}
		} else {
			skillsString += "empty";
		}
		// deleting the last space
		skillsString = skillsString.substring(0, skillsString.length() - 1);
		skillsString += "]";
		return String.format("member{@id=%1$s, name=%2$s, skills=%3$s}", id.getId(), name, skillsString);
	}
}
