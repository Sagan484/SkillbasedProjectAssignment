package com.fse.membermanagement.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.fse.membermanagement.adapter.listener.CheckSkillsEventListener;
import com.fse.membermanagement.adapter.messaging.MessagingService;
import com.fse.membermanagement.adapter.publisher.MessagingServiceImpl;
import com.fse.membermanagement.application.MemberManagementApplicationService;
import com.fse.membermanagement.application.MemberManagementService;
import com.fse.membermanagement.application.MemberToMemberDTOMapper;
import com.fse.membermanagement.application.SkillToSkillDTOMapper;
import com.fse.membermanagement.domain.repositories.MemberRepository;
import com.fse.membermanagement.domain.services.MemberService;
import com.fse.membermanagement.infrastructure.repositories.JdbcMemberEntityRepository;
import com.fse.membermanagement.infrastructure.repositories.MemberRepositoryImpl;

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

