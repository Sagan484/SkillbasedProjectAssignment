package com.fse.skillmanagement.adapter.messaging;

import com.fse.skillmanagement.domain.events.DomainEvent;

public interface MessagingService {
	
	public <T extends DomainEvent> void send(T event);
}
