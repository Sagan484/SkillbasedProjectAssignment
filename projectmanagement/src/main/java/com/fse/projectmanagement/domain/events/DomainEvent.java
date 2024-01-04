package com.fse.projectmanagement.domain.events;

public class DomainEvent {
	
	private String payload;
	
	public DomainEvent(String payload) {
		this.payload = payload;
	}

	public String getPayload() {
		return payload;
	}
}
