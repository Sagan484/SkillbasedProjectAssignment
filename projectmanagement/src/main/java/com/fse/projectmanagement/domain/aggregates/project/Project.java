package com.fse.projectmanagement.domain.aggregates.project;

import java.util.Set;

public class Project {

	private ProjectId id;
	private String name;
	private Set<Member> members;
	
	public Project(ProjectId id, String name, Set<Member> members) {
		this.id = id;
		this.name = name;
		this.members = members;
	}

	public void changeName(String name) {
		this.name = name;
	}

	
	public void addMember(Member member) {
		members.add(member);
	}
	
	public boolean removeMember(MemberId id) {
		Member m = getMember(id);
		return members.remove(m);
	}
	
	private Member getMember(MemberId id) {
		return members.stream()
				.filter(member -> member.getMemberId().equals(id))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Member with id " + id.getId() + " not assigned to project " + getProjectId().getId()));
	}
	
	public Set<Member> getMembers() {
		return members;
	}
	
	public ProjectId getProjectId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
