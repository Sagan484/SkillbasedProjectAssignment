package com.fse.skillmanagement;

public class SkillId {

	private int id;
	
	public SkillId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public boolean equals (Object o) {
		SkillId a = (SkillId)o;
		return (this.id == a.id);
	}
}
