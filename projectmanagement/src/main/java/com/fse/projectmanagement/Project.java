package com.fse.projectmanagement;

import java.util.HashSet;
import java.util.Set;

public class Project {

	private ProjectId id;
	private String name;
	private Set<Member> members = new HashSet<>();
	
	public Project(ProjectId id, String name, Set<Member> members) {
		this.id = id;
		this.name = name;
		this.members = members;
	}
	
	public Project(ProjectId id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public ProjectEntity toDatabase() {
		return new ProjectEntity(this);
	}
	
	public ProjectId getProjectId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void changeName(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		String membersString = "[";
		if (!members.isEmpty()) {
			for (Member m : members) {
				membersString += m.toString();
			}
			// deleting the last space
			membersString = membersString.substring(0, membersString.length() - 1);
		}
		membersString += "]";
		return String.format("project{@id=%1$s, name=%2$s, members=%3$s}", id.getId() , name, membersString);
	}
}
