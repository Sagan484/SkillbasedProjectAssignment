package com.fse.skillmanagement.adapter.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import com.fse.skillmanagement.adapter.messaging.MessagingService;
import com.fse.skillmanagement.domain.events.DomainEvent;
import com.fse.skillmanagement.domain.events.MemberDataChangedEvent;
import com.fse.skillmanagement.infrastructure.config.PropertiesConfig;

public class MessagingServiceImpl implements MessagingService {

	private RabbitTemplate rabbitTemplate;
	private PropertiesConfig config;
	private KafkaTemplate<String, String> kafkaTemplate;

	public MessagingServiceImpl(RabbitTemplate rabbitTemplate, KafkaTemplate<String, String> kafkaTemplate, PropertiesConfig config) {
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
		kafkaTemplate.send(config.getTopicName(), event.getPayload());
		}
	}
}
