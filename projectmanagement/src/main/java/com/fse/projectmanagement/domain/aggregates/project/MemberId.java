package com.fse.projectmanagement.domain.aggregates.project;

public class MemberId {

	private Integer id;
	
	public MemberId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	public boolean equals (MemberId o) {
		if(id.compareTo(o.id) == 0) {
			return true;
		}
		return false;
	}
}
