package com.fse.projectmanagement;

public class ProjectId {

	private int id;
	
	public ProjectId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public boolean equals (Object o) {
		ProjectId a = (ProjectId)o;
		return (this.id == a.id);
	}
}
