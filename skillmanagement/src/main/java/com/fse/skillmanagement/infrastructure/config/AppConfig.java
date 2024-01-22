package com.fse.skillmanagement.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.fse.skillmanagement.adapter.listener.CheckSkillsEventListener;
import com.fse.skillmanagement.adapter.messaging.MessagingService;
import com.fse.skillmanagement.adapter.publisher.MessagingServiceImpl;
import com.fse.skillmanagement.application.MemberManagementApplicationService;
import com.fse.skillmanagement.application.MemberManagementService;
import com.fse.skillmanagement.application.MemberToMemberDTOMapper;
import com.fse.skillmanagement.application.SkillToSkillDTOMapper;
import com.fse.skillmanagement.domain.repositories.MemberRepository;
import com.fse.skillmanagement.domain.services.MemberService;
import com.fse.skillmanagement.infrastructure.repositories.JdbcMemberEntityRepository;
import com.fse.skillmanagement.infrastructure.repositories.MemberRepositoryImpl;

@Configuration
public class AppConfig {
	
	@Bean
	MemberManagementService memberManagementService(MemberRepository memberRepository,
			MemberService memberService,
			SkillToSkillDTOMapper skillMapper) {
		return new MemberManagementApplicationService(memberRepository, memberService, skillMapper);
	}

	@Bean
	MemberRepository memberRepository(JdbcMemberEntityRepository jdbcMemberEntityRepository) {
		return new MemberRepositoryImpl(jdbcMemberEntityRepository);
	}
	
	@Bean
	MemberService memberService(MemberRepository memberRepository, MessagingService messagingService) {
		return new MemberService(memberRepository, messagingService);
	}
	
	@Bean
	SkillToSkillDTOMapper skillMapper() {
		return new SkillToSkillDTOMapper();
	}
	
	@Bean
	MemberToMemberDTOMapper memberMapper() {
		return new MemberToMemberDTOMapper();
	}
	
	@Bean
	CheckSkillsEventListener removeMemberEventListener(MemberService memberService, PropertiesConfig config) {
		return new CheckSkillsEventListener(memberService, config);
	}
	
	@Bean
	MessagingService messagingService(RabbitTemplate rabbitTemplate,
			KafkaTemplate<String, String> kafkaTemplate,
			PropertiesConfig config) {
		return new MessagingServiceImpl(rabbitTemplate, kafkaTemplate, config);
	}
	
	@Bean PropertiesConfig config() {
		return new PropertiesConfig();
	}
}

