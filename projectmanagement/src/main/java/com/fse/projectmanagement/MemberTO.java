package com.fse.projectmanagement;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MemberTO {
		
	int id;
	
	@JsonCreator
	public MemberTO(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
