package com.fse.skillmanagement;

public class MemberId {

	private int id;

	public MemberId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean equals(Object o) {
		MemberId a = (MemberId) o;
		return (this.id == a.id);
	}
}