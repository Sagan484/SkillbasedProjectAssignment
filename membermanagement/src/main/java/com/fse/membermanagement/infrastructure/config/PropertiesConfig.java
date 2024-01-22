package com.fse.membermanagement.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

	@Value("${rabbitmq.queue.check.skills.name}")
	private String queueCheckSkillsName;
	
	@Value("${rabbitmq.queue.check.skills.durable}")
	private boolean isQueueCheckSkillsDurable;
	
	@Value("${rabbitmq.queue.check.skills.routing.key}")
	private String queueCheckSkillsRoutingKey;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	
	@Value("${kafka.topic.name.listener}")
    private String topicNameListener;
	
	@Value("${kafka.topic.name.publisher}")
    private String topicNamePublisher;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	public String getQueueCheckSkillsName() {
		return queueCheckSkillsName;
	}

	public boolean isQueueCheckSkillsDurable() {
		return isQueueCheckSkillsDurable;
	}

	public String getQueueCheckSkillsRoutingKey() {
		return queueCheckSkillsRoutingKey;
	}

	public String getExchangeName() {
		return exchangeName;
	}
	
	public String getTopicNameListener() {
		return topicNameListener;
	}
	
	public String getTopicNamePublisher() {
		return topicNamePublisher;
	}
	
	public String getGroupId() {
		return groupId;
	}
}
