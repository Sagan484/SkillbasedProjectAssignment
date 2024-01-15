package com.fse.skillmanagement.domain.aggregates.member;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
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
	
	public void changeName(String name) {
		this.name = name;
	}

	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	public void removeSkill(Skill skill) {
		Skill s = getSkill(skill.getName(), skill.getArea());
		skills.remove(s);
	}
	
	private Skill getSkill(String name, String area) {
		return skills.stream()
				.filter(skill -> skill.getName().equals(name) && skill.getArea().equals(area))
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException(
						"Skill '" + name + "' not assigned to member " + getMemberId().getId()));
	}
	
	public boolean areSkillsValid(List<String> requirements) {
		return skills.stream()
				.anyMatch(s -> requirements.stream()
						.anyMatch(r -> s.getName().equals(r)));
	}

	public MemberId getMemberId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<Skill> getSkills() {
		return skills;
	}
}
