package com.fse.skillmanagement;

import java.util.HashSet;
import java.util.Set;

public class Skill {
	private SkillId id;
	private String name;
	private String area;
	private Set<Member> members = new HashSet<>();

	public Skill(SkillId id, String name, String area, Set<Member> members) {
		this.id = id;
		this.name = name;
		this.area = area;
		this.members = members;
	}

	public Skill(SkillId id, String name, String area) {
		this.id = id;
		this.name = name;
		this.area = area;
	}

	public SkillId getSkillId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void changeName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}
	
	public void changeArea(String area) {
		this.area = area;
	}

	public Set<Member> getMembers() {
		return members;
	}
	
	public void addMember(Member member) {
		members.add(member);
	}
	
	public void removeMember(Member member) {
		members.remove(member);
	}

	public String toString() {
		return String.format("member{@id=%1$s, name=%2$s, area=%3$s} ", id.getId(), name, area);
	}
}