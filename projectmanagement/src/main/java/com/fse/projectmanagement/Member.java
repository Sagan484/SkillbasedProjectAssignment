package com.fse.projectmanagement;

public class Member {
	private MemberId id;
	private String name;
	private Project project;
	
	public Member(MemberId id, String name, Project project) {
		this.id = id;
		this.name = name;
		this.project = project;
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
	
	public Project getProject() {
		return project;
	}
	
	public String toString() {
		return String.format("member{@id=%1$s, name=%2$s} ", getMemberId().getId() ,getName());
	}
}
