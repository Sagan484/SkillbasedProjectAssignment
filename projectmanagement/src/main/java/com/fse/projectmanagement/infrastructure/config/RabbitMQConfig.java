package com.fse.projectmanagement.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Autowired
	private PropertiesConfig config;

	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange(config.getExchangeName());
	}

	@Bean
	Queue manageMembers() {
		return new Queue(config.getQueueManageMembersName(), config.isQueueManageMembersDurable());
	}

	@Bean
	Binding bindingManageMembers(Queue manageMembers) {
		return BindingBuilder.bind(manageMembers).to(topicExchange()).with(config.getQueueManageMembersRoutingKey());
	}
	
	@Bean PropertiesConfig config() {
		return new PropertiesConfig();
	}
}

