package com.fse.projectmanagement.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

	@Value("${rabbitmq.queue.manage.members.name}")
	private String queueManageMembersName;
	
	@Value("${rabbitmq.queue.manage.members.durable}")
	private boolean isQueueManageMembersDurable;
	
	@Value("${rabbitmq.queue.manage.members.routing.key}")
	private String queueManageMembersRoutingKey;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	
	@Value("${spring.kafka.topic.name}")
	private String topicName;

	public String getQueueManageMembersName() {
		return queueManageMembersName;
	}

	public boolean isQueueManageMembersDurable() {
		return isQueueManageMembersDurable;
	}

	public String getQueueManageMembersRoutingKey() {
		return queueManageMembersRoutingKey;
	}

	public String getExchangeName() {
		return exchangeName;
	}
	
	public String getTopicName() {
		return topicName;
	}
}
