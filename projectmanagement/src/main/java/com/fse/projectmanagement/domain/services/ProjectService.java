package com.fse.projectmanagement.domain.services;

import com.fse.projectmanagement.adapter.messaging.MessagingService;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.MemberId;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.events.MemberTemporalyAddedEvent;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;

public class ProjectService {
	
	private ProjectRepository projectRepository;
	private MessagingService messagingService;
		
	public ProjectService(ProjectRepository projectRepository, MessagingService messagingService) {
		this.projectRepository = projectRepository;
		this.messagingService = messagingService;
	}
	
	public void changeProjectName(Integer id, String name) {
		Project project = projectRepository.findById(id);
		project.changeName(name);
		projectRepository.save(project);
	}

	public String addMember(Integer id, Member member) {
		Project project = projectRepository.findById(id);
		String response = messagingService.sendAndReceiveViaRabbit(new MemberTemporalyAddedEvent(project, member));
		//String response = messagingService.sendAndReceiveViaKafka(new MemberTemporalyAddedEvent(project, member));
		String result = response;
		if (response.equalsIgnoreCase("true") || response.equalsIgnoreCase("false")) {
			boolean areSkillsValid = Boolean.valueOf(response);
			if (areSkillsValid) {
				project.addMember(member);
				if (projectRepository.save(project) != -1) {
					result = "Member successfully added.";
				} else {
					result = "Member was already assigned to project.";
				}
			} else {
				result = "Member is missing some skill.";
			}
		}
		return result;
	}
	
	public void removeMember(Integer id, MemberId memberId) {
		Project project = projectRepository.findById(id);
		project.removeMember(memberId);
		projectRepository.save(project);
	}
}
