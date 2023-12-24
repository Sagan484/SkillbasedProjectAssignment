package com.fse.projectmanagement.domain.services;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;

public class ProjectService {
	
	private ProjectRepository projectRepository;
	
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public void changeProjectName(Integer id, String name) {
		Project project = projectRepository.findById(id);
		project.changeName(name);
		projectRepository.save(project);
	}
	
	public void addMember(Integer id, Member member) {
		Project project = projectRepository.findById(id);
		project.addMember(member);
		projectRepository.save(project);
	}
	
	public void removeMember(Integer id, MemberId memberId) {
		Project project = projectRepository.findById(id);
		project.removeMember(memberId);
		projectRepository.save(project);
	}
}
