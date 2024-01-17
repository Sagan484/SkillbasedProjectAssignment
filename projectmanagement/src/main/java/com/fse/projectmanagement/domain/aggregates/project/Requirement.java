package com.fse.projectmanagement.domain.aggregates.project;

public class Requirement {
	
	private String name;
	
	public Requirement(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s", name);
	}
}
