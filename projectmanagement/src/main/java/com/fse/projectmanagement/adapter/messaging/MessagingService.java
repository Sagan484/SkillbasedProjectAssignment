package com.fse.projectmanagement.adapter.messaging;

import com.fse.projectmanagement.domain.events.DomainEvent;

public interface MessagingService {
	
	public <T extends DomainEvent> Object send(T event);
}
