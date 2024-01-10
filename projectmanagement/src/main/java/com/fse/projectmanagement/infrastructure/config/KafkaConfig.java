package com.fse.projectmanagement.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
	
	@Autowired
	private PropertiesConfig config;

	    @Bean
	    public NewTopic javaguidesTopic(){
	        return TopicBuilder.name(config.getTopicName())
	                .build();
	    }

}
