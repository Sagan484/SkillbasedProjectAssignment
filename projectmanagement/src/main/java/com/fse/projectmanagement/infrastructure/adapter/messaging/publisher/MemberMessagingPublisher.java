package com.fse.projectmanagement.infrastructure.adapter.messaging.publisher;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

import com.fse.projectmanagement.domain.events.DomainEvent;
import com.fse.projectmanagement.domain.events.MemberTemporalyAddedEvent;
import com.fse.projectmanagement.infrastructure.adapter.messaging.MessagingService;
import com.fse.projectmanagement.infrastructure.config.PropertiesConfig;

public class MemberMessagingPublisher implements MessagingService {
	
	private RabbitTemplate rabbitTemplate;
	private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;
	private PropertiesConfig config;
	
	public MemberMessagingPublisher(RabbitTemplate rabbitTemplate,
			ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate,
			PropertiesConfig config) {
		this.rabbitTemplate = rabbitTemplate;
		this.replyingKafkaTemplate = replyingKafkaTemplate;
		this.config = config;
	}

	@Override
	public <T extends DomainEvent> String sendAndReceiveViaRabbit(T event) {
		if (event.getClass().equals(MemberTemporalyAddedEvent.class)) {
			rabbitTemplate.setReplyTimeout(60000);
			Object response = rabbitTemplate.convertSendAndReceive(config.getExchangeName(), "member.added",
					event.getPayload());
			if (response != null) {
				return (String) response;
			}
			return "Response could not be received.";
		}
		return null;
	}
	
	@Override
	public <T extends DomainEvent> String sendAndReceiveViaKafka(T event) {
		String response = null;
		if (event.getClass().equals(MemberTemporalyAddedEvent.class)) {
	        try {
	        	ProducerRecord<String, String> record = new ProducerRecord<>(config.getTopicNamePublisher(), event.getPayload());
		        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, config.getTopicNameReply().getBytes()));
		        RequestReplyFuture<String, String, String> replyFuture = replyingKafkaTemplate.sendAndReceive(record, Duration.ofSeconds(30));
				SendResult<String, String> sendResult = replyFuture.getSendFuture().get(30, TimeUnit.SECONDS);
				System.out.println("Sent ok: " + sendResult.getRecordMetadata());
				ConsumerRecord<String, String> consumerRecord = replyFuture.get(30, TimeUnit.SECONDS);
				System.out.println("Consumer Record Topic: " + consumerRecord.topic());
		        response = consumerRecord.value();
	        } catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				response = "Timeout occured. Message could not be received.";
			} catch (TimeoutException e) {
				response = "Timeout occured. Message could not be sent.";
			}
		}
		return response;
	}
}
