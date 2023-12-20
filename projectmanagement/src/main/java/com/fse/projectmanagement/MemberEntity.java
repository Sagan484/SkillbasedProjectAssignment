package com.fse.projectmanagement;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "MEMBER")
public class MemberEntity {

	@Id
	private int id;
	private String name;
	private Integer projectId;
	
	/** empty constructor needed for creating an object while working with the {@link JdbcMemberEntityRepository} */
	public MemberEntity() {}
	
	public MemberEntity(Member member) {
		id = member.getMemberId().getId();
		name = member.getName();
		ProjectId pId = member.getProjectId();
		if (pId != null) {
			projectId = pId.getId();
		} else {
			projectId = null;
		}
	}

	public Member toDomain() {
		if (projectId != null) {
			return new Member(new MemberId(id), name, new ProjectId(projectId));
		}
		return new Member(new MemberId(id), name);
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
	
	public void setProjectId(int id) {
		projectId = id;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public String toString() {
		return String.format("memberentity{@id=%1$s, name=%2$s, projectId=%3$s} ", id, name, projectId);
	}
}
