package com.fse.skillmanagement.domain.events;

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
