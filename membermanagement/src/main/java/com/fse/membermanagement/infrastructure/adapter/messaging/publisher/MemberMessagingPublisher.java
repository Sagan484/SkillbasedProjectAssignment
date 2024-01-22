package com.fse.membermanagement.infrastructure.adapter.messaging.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import com.fse.membermanagement.domain.events.DomainEvent;
import com.fse.membermanagement.domain.events.MemberDataChangedEvent;
import com.fse.membermanagement.infrastructure.adapter.messaging.MessagingService;
import com.fse.membermanagement.infrastructure.config.PropertiesConfig;

public class MemberMessagingPublisher implements MessagingService {

	private RabbitTemplate rabbitTemplate;
	private PropertiesConfig config;
	private KafkaTemplate<String, String> kafkaTemplate;

	public MemberMessagingPublisher(RabbitTemplate rabbitTemplate,
			KafkaTemplate<String, String> kafkaTemplate,
			PropertiesConfig config) {
		this.rabbitTemplate = rabbitTemplate;
		this.kafkaTemplate = kafkaTemplate;
		this.config = config;
	}

	@Override
	public <T extends DomainEvent> void sendViaRabbit(T event) {
		if (event.getClass().equals(MemberDataChangedEvent.class)) {
			rabbitTemplate.convertAndSend(config.getExchangeName(), "member.changed", event.getPayload());
		}
	}

	@Override
	public <T extends DomainEvent> void sendViaKafka(T event) {
		if (event.getClass().equals(MemberDataChangedEvent.class)) {
			kafkaTemplate.send(config.getTopicNamePublisher(), event.getPayload());
		}
	}
}
