package com.fse.skillmanagement.adapter.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fse.skillmanagement.adapter.messaging.MessagingService;
import com.fse.skillmanagement.domain.events.DomainEvent;
import com.fse.skillmanagement.domain.events.MemberDataChangedEvent;
import com.fse.skillmanagement.infrastructure.config.PropertiesConfig;

public class MessagingServiceImpl implements MessagingService {

	private RabbitTemplate rabbitTemplate;
	private PropertiesConfig config;

	public MessagingServiceImpl(RabbitTemplate rabbitTemplate, PropertiesConfig config) {
		this.rabbitTemplate = rabbitTemplate;
		this.config = config;
	}

	@Override
	public <T extends DomainEvent> void send(T event) {
		if (event.getClass().equals(MemberDataChangedEvent.class)) {
			rabbitTemplate.convertAndSend(config.getExchangeName(), "member.changed", event.getPayload());
		}
	}
}
