package com.fse.projectmanagement;

public class Member {
	private MemberId id;
	private String name;
	private ProjectId projectId;
	
	public Member(MemberId id, String name, ProjectId projectId) {
		this.id = id;
		this.name = name;
		this.projectId = projectId;
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
	
	public void assignToProject(ProjectId projectId) {
		this.projectId = projectId;
	}
	
	public boolean unassignFromProject(ProjectId projectId) {
		int requestedProjectId = projectId.getId();
		if (this.projectId.getId() != requestedProjectId) {
			System.out.println("The member is not part of the project " + requestedProjectId);
			return false;
		} else {
			this.projectId = null;
			return true;
		}
	}
	
	public ProjectId getProjectId() {
		return projectId;
	}
	
	public String toString() {
		return String.format("member{@id=%1$s, name=%2$s, projectId=%3$s} ", id.getId(), name, projectId.getId());
	}
}
