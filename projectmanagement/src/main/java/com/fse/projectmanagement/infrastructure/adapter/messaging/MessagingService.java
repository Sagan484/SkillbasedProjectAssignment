package com.fse.projectmanagement.infrastructure.adapter.messaging;

import com.fse.projectmanagement.domain.events.DomainEvent;

public interface MessagingService {
	
	public <T extends DomainEvent> String sendAndReceiveViaRabbit(T event);
	public <T extends DomainEvent> String sendAndReceiveViaKafka(T event);
}
