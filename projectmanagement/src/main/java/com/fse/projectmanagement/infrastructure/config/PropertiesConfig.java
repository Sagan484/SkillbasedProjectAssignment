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
	
	@Value("${kafka.topic.name.listener}")
    private String topicNameListener;
	
	@Value("${kafka.topic.name.publisher}")
    private String topicNamePublisher;
	
	@Value("${kafka.topic.name.reply}")
    private String topicNameReply;
	
	@Value("${spring.kafka.consumer.group-id}")
    private String groupId;
	
	@Value("${spring.kafka.consumer.bootstrap-servers:}")
    private String consumerServers;
	
	@Value("${spring.kafka.producer.bootstrap-servers:}")
    private String producerServers;

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
	
	public String getTopicNameListener() {
		return topicNameListener;
	}
	
	public String getTopicNamePublisher() {
		return topicNamePublisher;
	}
	
	public String getTopicNameReply() {
		return topicNameReply;
	}

	public String getGroupId() {
		return groupId;
	}
	
	public String getConsumerServers() {
		return consumerServers;
	}

	public String getProducerServers() {
		return producerServers;
	}
}
