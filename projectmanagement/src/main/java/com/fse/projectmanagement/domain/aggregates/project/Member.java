package com.fse.projectmanagement.domain.aggregates.project;

public class Member {
	
	private MemberId id;
	private String name;
	
	public Member(MemberId id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void changeName(String name) {
		this.name = name;
	}

	public MemberId getMemberId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format("member ID: %s, name: %s", id.getId(), name);
	}
}
