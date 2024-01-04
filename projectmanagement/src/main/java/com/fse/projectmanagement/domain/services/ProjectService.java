package com.fse.projectmanagement.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.projectmanagement.adapter.messaging.MessagingService;
import com.fse.projectmanagement.application.MemberDTO;
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
	
	public void deleteProject(Integer id) {
		Project project = projectRepository.findById(id);
		projectRepository.delete(project);
	}

	public String addMember(Integer id, Member member) {
		Project project = projectRepository.findById(id);
		List<String> requirements = project.getRequirements().stream().map(r -> r.getName())
				.collect(Collectors.toList());
		MemberDTO memberDto = new MemberDTO(member.getMemberId().getId(), member.getName());
		String response = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String payload = objectMapper.writeValueAsString(memberDto) + "\n"
					+ objectMapper.writeValueAsString(requirements);
			response = messagingService.send(new MemberTemporalyAddedEvent(payload));
		} catch (JsonProcessingException e) {
			System.out.println("Message could not be send. Cause: " + e.getMessage());
		}
		if (response.contains("No member found with id ")) {
			return response;
		}
		boolean areSkillsValid = Boolean.valueOf(response);
		if (areSkillsValid) {
			project.addMember(member);
			projectRepository.save(project);
			return "Member successfully added.";
		}
		return "Member is missing some skill.";
	}

	public void removeMember(Integer id, MemberId memberId) {
		Project project = projectRepository.findById(id);
		project.removeMember(memberId);
		projectRepository.save(project);
	}
}
