package com.fse.projectmanagement;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "MEMBER")
public class MemberEntity {

	@Id
	private int id;
	private String name;
	@Transient
	private ProjectEntity projectEntity;
	
	/** empty constructor needed for creating an object while working with the {@link JdbcMemberEntityRepository} */
	public MemberEntity() {}
	
	public MemberEntity(Member member) {
		id = member.getMemberId().getId();
		name = member.getName();
		projectEntity = member.getProject().toDatabase();
	}

	public Member toDomain() {
		if (projectEntity != null) {
			Project p = projectEntity.toDomain();
			return new Member(new MemberId(id), name, p);
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
	
	public ProjectEntity getProjectEntity() {
		return projectEntity;
	}
	
	public String toString() {
		return String.format("memberentity{@id=%1$s, name=%2$s} ", getId() ,getName());
	}
}
