package com.fse.skillmanagement.adapter.messaging;

import com.fse.skillmanagement.domain.events.DomainEvent;

public interface MessagingService {
	
	public <T extends DomainEvent> void sendViaRabbit(T event);
	public <T extends DomainEvent> void sendViaKafka(T event);
}
