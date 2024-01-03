package com.fse.skillmanagement.domain.aggregates.member;

public class Skill {
	private String name;
	private String area;

	public Skill(String name, String area) {
		this.name = name;
		this.area = area;
	}

	public String getName() {
		return name;
	}
	
	public void changeName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}
	
	public void changeArea(String area) {
		this.area = area;
	}
}