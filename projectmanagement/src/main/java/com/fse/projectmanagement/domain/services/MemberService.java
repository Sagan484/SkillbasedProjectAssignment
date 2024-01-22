package com.fse.projectmanagement.domain.services;

import java.util.List;
import java.util.NoSuchElementException;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;

public class MemberService {

	private ProjectRepository projectRepository;

	public MemberService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public void changeMemberData(Member newMember) {
		List<Project> projects = projectRepository.findAll();
		MemberId memberId = newMember.getMemberId();
		Project project = null;
		// find the project and remove the old member
		for (Project p:projects) {
			try {
				p.removeMember(memberId);
				project = p;
				break;
			} catch (NoSuchElementException e) {
				// do nothing. move on
			}
		}
		// replace with new member data
		if (project != null) {
			project.addMember(newMember);
			projectRepository.save(project);
		} else {
			throw new NoSuchElementException();
		}
	}
}
