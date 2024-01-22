package com.fse.membermanagement.adapter.messaging;

import com.fse.membermanagement.domain.events.DomainEvent;

public interface MessagingService {
	
	public <T extends DomainEvent> void sendViaRabbit(T event);
	public <T extends DomainEvent> void sendViaKafka(T event);
}
