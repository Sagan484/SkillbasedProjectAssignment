package com.fse.skillmanagement;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SkillTO {
	private int id;
	
	@JsonCreator
	public SkillTO (int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}