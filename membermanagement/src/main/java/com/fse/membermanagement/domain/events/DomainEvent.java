package com.fse.membermanagement.domain.events;

public class DomainEvent {
	
	protected String payload;
	
	public DomainEvent() {}
	
	public DomainEvent(String payload) {
		this.payload = payload;
	}

	public String getPayload() {
		return payload;
	}
}
