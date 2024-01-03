package com.fse.projectmanagement.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

	@Value("${rabbitmq.queue.remove.members.name}")
	private String queueManageMembersName;
	
	@Value("${rabbitmq.queue.remove.members.durable}")
	private boolean isQueueManageMembersDurable;
	
	@Value("${rabbitmq.queue.remove.members.routing.key}")
	private String queueManageMembersRoutingKey;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;

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
}
