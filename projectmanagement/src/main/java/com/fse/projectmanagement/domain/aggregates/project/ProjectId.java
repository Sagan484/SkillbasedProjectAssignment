package com.fse.projectmanagement.domain.aggregates.project;

public class ProjectId {

	private Integer id;

	public ProjectId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public boolean equals (ProjectId o) {
		if(id.compareTo(o.id) == 0) {
			return true;
		}
		return false;
	}
}
