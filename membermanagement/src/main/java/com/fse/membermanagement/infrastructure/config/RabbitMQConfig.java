package com.fse.membermanagement.infrastructure.config;

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
	Queue checkSkills() {
		return new Queue(config.getQueueCheckSkillsName(), config.isQueueCheckSkillsDurable());
	}

	@Bean
	Binding bindingCheckSkills(Queue checkSkills) {
		return BindingBuilder.bind(checkSkills).to(topicExchange()).with(config.getQueueCheckSkillsRoutingKey());
	}
}

