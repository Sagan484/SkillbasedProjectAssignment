package com.fse.projectmanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.NoSuchElementException;
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
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.domain.services.ProjectService;
import com.fse.projectmanagement.infrastructure.entities.ProjectEntity;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableJdbcRepositories
public class ProjectmanagementApplicationTests {

	@Autowired
	private ProjectRepository projectRepository;
	
	private Integer pId = 9000;
	private Integer mId = 1000;
	private Set<Requirement> requirements = new HashSet<>();
	private Set<Member> members = new HashSet<>();

	@Test
	void testShouldThrowNPEIfMembersAreNull() {
		assertThrows(NullPointerException.class,
	            ()->{
	            new ProjectEntity(new Project(new ProjectId(pId), "Test Projekt", null, requirements));
	            });
	}
	
	@Test
	void testShouldThrowNPEIfRequirementsAreNull() {
		assertThrows(NullPointerException.class,
	            ()->{
	            new ProjectEntity(new Project(new ProjectId(pId), "Test Projekt", members, null));
	            });
	}
	
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
	
	@Test
	void testChangingProjectName() {
		Project project = new Project(new ProjectId(null), "Test Projekt", members, requirements);
		project.changeName("neuer Name");
		assertEquals("neuer Name", project.getName());
	}
	
	@Test
	void testAddingMember() {
		MemberId memberId = new MemberId(mId);
		Member member = new Member(memberId, "TestMember");
		Project project = new Project(new ProjectId(null), "Test Projekt", members, requirements);
		project.addMember(member);
		assertThat(project.getMembers())
        .hasSize(1)
        .extracting(Member::getMemberId)
        .containsExactly(memberId); 
	}
	
	@Test
	void testRemovingMember() {
		MemberId memberId = new MemberId(mId);
		Member member = new Member(memberId, "TestMember");
		members.add(member);
		Project project = new Project(new ProjectId(null), "Test Projekt", members, requirements);
		project.removeMember(member.getMemberId());
		assertThat(project.getMembers()).hasSize(0);
	}
	
	@Test
	void testShouldThrowNoSuchElementFoundExceptionIfNoMemberFoundForRemovingMember() {
		Project project = new Project(new ProjectId(pId), "Test Projekt", members, requirements);
		assertThrows(NoSuchElementException.class,
				()->{ project.removeMember(new MemberId(mId));
				}, "Member with id " + mId + " not assigned to project " + pId);
	}
}
