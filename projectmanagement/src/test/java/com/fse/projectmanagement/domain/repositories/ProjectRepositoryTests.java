package com.fse.projectmanagement.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableJdbcRepositories
class ProjectRepositoryTests {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	private Integer mId = 1000;
	private Set<Member> members = new HashSet<>();
	private Set<Requirement> requirements = new HashSet<>();
	
	@Test
	@Transactional
	void testIfProjectWithSameIDIsUpdated() {
		Project project1 = new Project(new ProjectId(null), "Test Projekt", members, requirements);
		Integer projectId1 = projectRepository.save(project1);
		Project project2 = new Project(new ProjectId(projectId1), "Test Projekt", members, requirements);
		Integer projectId2 = projectRepository.save(project2);
		assertEquals(projectId1, projectId2);
	}
	
	@Test
	@Transactional
	void testIfMemberIsAlreadyAssignedToProject() {
		Member member = new Member(new MemberId(mId), "TestMember");
		Project project = new Project(new ProjectId(null), "Test Projekt", members, requirements);
		project.addMember(member);
		Integer projectId1 = projectRepository.save(project);
		project.addMember(member);
		Integer projectId2 = projectRepository.save(project);
		assertNotEquals(-1, projectId1);
		assertEquals(-1, projectId2);
	}

}
