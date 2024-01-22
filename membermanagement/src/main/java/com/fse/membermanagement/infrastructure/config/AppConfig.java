package com.fse.membermanagement.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.fse.membermanagement.application.MemberManagementApplicationService;
import com.fse.membermanagement.application.MemberManagementApplicationServiceImpl;
import com.fse.membermanagement.domain.repositories.MemberRepository;
import com.fse.membermanagement.domain.services.MemberService;
import com.fse.membermanagement.infrastructure.adapter.messaging.MessagingService;
import com.fse.membermanagement.infrastructure.adapter.messaging.listener.CheckSkillsEventListener;
import com.fse.membermanagement.infrastructure.adapter.messaging.publisher.MemberMessagingPublisher;
import com.fse.membermanagement.infrastructure.mapper.MemberToMemberDTOMapper;
import com.fse.membermanagement.infrastructure.mapper.SkillToSkillDTOMapper;
import com.fse.membermanagement.infrastructure.repositories.JdbcMemberEntityRepository;
import com.fse.membermanagement.infrastructure.repositories.MemberRepositoryImpl;


@Configuration
public class AppConfig {
	
	@Bean
	MemberManagementApplicationService memberManagementApplicationService(MemberRepository memberRepository,
			MemberService memberService,
			SkillToSkillDTOMapper skillMapper) {
		return new MemberManagementApplicationServiceImpl(memberRepository, memberService, skillMapper);
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
	MemberMessagingPublisher messagingService(RabbitTemplate rabbitTemplate,
			KafkaTemplate<String, String> kafkaTemplate,
			PropertiesConfig config) {
		return new MemberMessagingPublisher(rabbitTemplate, kafkaTemplate, config);
	}
	
	@Bean PropertiesConfig config() {
		return new PropertiesConfig();
	}
}

