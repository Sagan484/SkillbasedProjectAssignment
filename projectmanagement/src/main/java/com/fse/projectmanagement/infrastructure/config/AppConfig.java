package com.fse.projectmanagement.infrastructure.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fse.projectmanagement.adapter.messaging.MessagingService;
import com.fse.projectmanagement.adapter.publisher.MessagingServiceImpl;
import com.fse.projectmanagement.application.DTOtoDomainMapper;
import com.fse.projectmanagement.application.MemberToMemberDTOMapper;
import com.fse.projectmanagement.application.ProjectManagementApplicationService;
import com.fse.projectmanagement.application.ProjectManagementService;
import com.fse.projectmanagement.application.RequirementToRequirementDTOMapper;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.domain.services.ProjectService;
import com.fse.projectmanagement.infrastructure.repositories.JdbcProjectEntityRepository;
import com.fse.projectmanagement.infrastructure.repositories.ProjectRepositoryImpl;

@Configuration
public class AppConfig {
	
	@Bean
	ProjectManagementService projectManagementService(ProjectRepository projectRepository,
			ProjectService projectService,
			MemberToMemberDTOMapper memberMapper,
			RequirementToRequirementDTOMapper requirementMapper,
			DTOtoDomainMapper dtoToDomainMapper) {
		return new ProjectManagementApplicationService(projectRepository,
				projectService,
				memberMapper,
				requirementMapper,
				dtoToDomainMapper);
	}

	@Bean
	ProjectRepository projectRepository(JdbcProjectEntityRepository jdbcProjectEntityRepository) {
		return new ProjectRepositoryImpl(jdbcProjectEntityRepository);
	}
	
	@Bean
	ProjectService projectService(ProjectRepository projectRepository, MessagingService messagingService) {
		return new ProjectService(projectRepository, messagingService);
	}
	
	@Bean MemberToMemberDTOMapper memberMapper() {
		return new MemberToMemberDTOMapper();
	}
	
	@Bean RequirementToRequirementDTOMapper requirementMappper() {
		return new RequirementToRequirementDTOMapper();
	}
	
	@Bean DTOtoDomainMapper dtoToDomainMapper() {
		return new DTOtoDomainMapper();
	}
	
	@Bean MessagingService messagingService(RabbitTemplate rabbitTemplate, PropertiesConfig config) {
		return new MessagingServiceImpl(rabbitTemplate, config);
	}
}

