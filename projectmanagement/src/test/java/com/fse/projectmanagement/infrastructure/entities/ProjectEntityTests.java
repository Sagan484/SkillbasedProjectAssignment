package com.fse.projectmanagement.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;

@SpringBootTest
class ProjectEntityTests {
	
	private Integer pId = 9000;
	private Set<Member> members = new HashSet<>();
	private Set<Requirement> requirements = new HashSet<>();

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
}
