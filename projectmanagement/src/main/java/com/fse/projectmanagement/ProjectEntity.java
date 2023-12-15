package com.fse.projectmanagement;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("PROJECT")
public class ProjectEntity {

	@Id
	private int id;
	private String name;
	private Set<MemberEntity> members = new HashSet<>();
	
	/** empty constructor needed for creating an object while working with the {@link JdbcProjectEntityRepository} */
	public ProjectEntity() {}
	
	public ProjectEntity(Project project) {
		id = project.getProjectId().getId();
		name = project.getName();
	}
	
	public Project toDomain() {
		if (members.isEmpty()) {
			return new Project(new ProjectId(id), name);
		}
		Set<Member> membersToDomain = new HashSet<>();
		for (MemberEntity member : members) {
			membersToDomain.add(member.toDomain());
		}
		return new Project(new ProjectId(id), name, membersToDomain);
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
	
	public Set<MemberEntity> getMembers() {
		return members;
	}
	
	public void setMembers(Set<MemberEntity> members) {
		this.members = members;
	}
	
	public String toString() {
		String membersString = "[";
		Set<MemberEntity> members = getMembers();
		if(members != null) {
			for (MemberEntity m : members) {
				membersString += m.toString();
			}
		} else {
			membersString += "empty";
		}
		// deleting the last space
		membersString = membersString.substring(0, membersString.length()-1);
		membersString += "]";
		return String.format("projectentity{@id=%1$s, name=%2$s, memberentities=%3$s}", getId() ,getName(), membersString);
	}
}
