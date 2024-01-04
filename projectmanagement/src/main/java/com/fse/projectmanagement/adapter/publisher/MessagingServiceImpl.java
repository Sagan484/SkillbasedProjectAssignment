package com.fse.projectmanagement.adapter.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fse.projectmanagement.adapter.messaging.MessagingService;
import com.fse.projectmanagement.domain.events.DomainEvent;
import com.fse.projectmanagement.domain.events.MemberTemporalyAddedEvent;
import com.fse.projectmanagement.infrastructure.config.PropertiesConfig;

public class MessagingServiceImpl implements MessagingService {
	
	private RabbitTemplate rabbitTemplate;
	private PropertiesConfig config;
	
	public MessagingServiceImpl(RabbitTemplate rabbitTemplate , PropertiesConfig config) {
		this.rabbitTemplate = rabbitTemplate;
		this.config = config;
	}

	@Override
	public <T extends DomainEvent> String send(T event) {
		if (event.getClass().equals(MemberTemporalyAddedEvent.class)) {
			rabbitTemplate.setReplyTimeout(10000);
			Object response = rabbitTemplate.convertSendAndReceive(
					config.getExchangeName(),
					"member.added",
					event.getPayload());
			return  (String) response;
		}
		return null;
	}
}
