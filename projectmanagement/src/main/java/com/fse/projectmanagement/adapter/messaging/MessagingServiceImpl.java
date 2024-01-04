package com.fse.projectmanagement.adapter.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fse.projectmanagement.domain.events.DomainEvent;
import com.fse.projectmanagement.infrastructure.config.PropertiesConfig;

public class MessagingServiceImpl implements MessagingService {
	
	private RabbitTemplate rabbitTemplate;
	private PropertiesConfig config;
	
	public MessagingServiceImpl(RabbitTemplate rabbitTemplate, PropertiesConfig config) {
		this.rabbitTemplate = rabbitTemplate;
		this.config = config;
		rabbitTemplate.setReceiveTimeout(30000);
	}

	@Override
	public <T extends DomainEvent> Boolean send(T event) {
		Boolean areRequirementsMet = (Boolean) rabbitTemplate.convertSendAndReceive(
				config.getExchangeName(),
				"member.added",
				event.getPayload(), new MessagePostProcessor() {
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setReplyTo(config.getQueueManageMembersName());
						return message;
					}
				});
		return areRequirementsMet;
	}

}
