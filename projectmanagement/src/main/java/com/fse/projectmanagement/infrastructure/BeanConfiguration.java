package com.fse.projectmanagement.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fse.projectmanagement.application.MemberToMemberDTOMapper;
import com.fse.projectmanagement.application.ProjectManagementApplicationService;
import com.fse.projectmanagement.application.ProjectManagementService;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.domain.services.ProjectService;
import com.fse.projectmanagement.infrastructure.repositories.JdbcProjectEntityRepository;
import com.fse.projectmanagement.infrastructure.repositories.ProjectRepositoryImpl;

@Configuration
public class BeanConfiguration {
	@Bean
	ProjectManagementService projectManagementService(ProjectRepository projectRepository, ProjectService projectService, MemberToMemberDTOMapper memberMapper) {
		return new ProjectManagementApplicationService(projectRepository, projectService, memberMapper);
	}

	@Bean
	ProjectRepository projectRepository(JdbcProjectEntityRepository jdbcProjectEntityRepository) {
		return new ProjectRepositoryImpl(jdbcProjectEntityRepository);
	}
	
	@Bean
	ProjectService projectService(ProjectRepository projectRepository) {
		return new ProjectService(projectRepository);
	}
	
	@Bean MemberToMemberDTOMapper memberMapper() {
		return new MemberToMemberDTOMapper();
	}
}

