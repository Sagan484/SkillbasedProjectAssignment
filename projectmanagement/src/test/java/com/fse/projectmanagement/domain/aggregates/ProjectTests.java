package com.fse.projectmanagement.domain.aggregates;

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

@SpringBootTest
public class ProjectTests {
	
	private Integer pId = 9000;
	private Integer mId = 1000;
	private Set<Requirement> requirements = new HashSet<>();
	private Set<Member> members = new HashSet<>();
	
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
