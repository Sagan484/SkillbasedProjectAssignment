package com.fse.projectmanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
	@Bean
	ProjectManagementService projectManagementService(ProjectRepository projectRepository,
			MemberRepository memberRepository) {
		return new ProjectManagementApplicationService(projectRepository, memberRepository);
	}

//	 @Bean
//	 EventListener eventListener(ProjectManagementService projectManagementService) {
//			return new EventListener(projectManagementService);
//	 };

	@Bean
	ProjectRepository projectRepository(JdbcProjectEntityRepository jdbcProjectEntityRepository) {
		return new ProjectEntityRepository(jdbcProjectEntityRepository);
	}

	@Bean
	MemberRepository memberRepository(JdbcMemberEntityRepository jdbcMemberEntityRepository) {
		return new MemberEntityRepository(jdbcMemberEntityRepository);
	}
}

