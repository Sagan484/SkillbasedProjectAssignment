package com.fse.projectmanagement.domain.events;

public class DomainEvent {
	
	protected String payload;
	
	public DomainEvent() {}

	public String getPayload() {
		return payload;
	}
}
