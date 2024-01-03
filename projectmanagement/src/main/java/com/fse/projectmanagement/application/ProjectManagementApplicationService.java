package com.fse.projectmanagement.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.amqp.core.AmqpTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.domain.services.ProjectService;
import com.fse.projectmanagement.infrastructure.config.PropertiesConfig;

public class ProjectManagementApplicationService implements ProjectManagementService {

	private ProjectRepository projectRepository;
	private ProjectService projectService;
	private MemberToMemberDTOMapper memberMapper;
	private RequirementToRequirementDTOMapper requirementMapper;
	private DTOtoDomainMapper dtoToDomainMapper;
	private AmqpTemplate amqpTemplate;
	private PropertiesConfig config;
	
	public ProjectManagementApplicationService(ProjectRepository projectRepository,
			ProjectService projectService,
			MemberToMemberDTOMapper memberMapper,
			RequirementToRequirementDTOMapper requirementMapper,
			DTOtoDomainMapper dtoToDomainMapper,
			AmqpTemplate amqpTemplate,
			PropertiesConfig config) {
		this.projectRepository = projectRepository;
		this.projectService = projectService;
		this.memberMapper = memberMapper;
		this.requirementMapper = requirementMapper;
		this.dtoToDomainMapper = dtoToDomainMapper;
		this.amqpTemplate = amqpTemplate;
		this.config = config;
	}
	
	@Override
	public Integer create(String name, Set<MemberDTO> mSet, Set<RequirementDTO> rSet) {
		Set<Member> members = dtoToDomainMapper.map(mSet);
		Set<Requirement> requirements = dtoToDomainMapper.map(rSet);
		// DTO erstellen und übergeben und im repository zu Project?
        Project p = new Project(new ProjectId(null), name, members, requirements);
        return projectRepository.save(p);
	}

	@Override
	public ProjectDTO read(Integer id) {
		try {
		Project p = projectRepository.findById(id);
		Set<MemberDTO> memberDTOs = memberMapper.map(p.getMembers());
		Set<RequirementDTO> requirementDTOs = requirementMapper.map(p.getRequirements());
		return new ProjectDTO(id, p.getName(), memberDTOs, requirementDTOs);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	@Override
	public List<ProjectDTO> readAll() {
		return 	projectRepository.findAll().stream()
	            .map(p -> new ProjectDTO(p.getProjectId().getId(), p.getName(), memberMapper.map(p.getMembers()), requirementMapper.map(p.getRequirements())))
	            .collect(Collectors.toList());
	}
	
	@Override
	public String changeProjectName(Integer id, String name) {
		try {
			projectService.changeProjectName(id, name);
			return "Project updated successfully.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}

	@Override
	public String delete(Integer id) {
		try {
			projectService.deleteProject(id);
			return "Project with id " + id + " successfully deleted.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}

	@Override
	public String assignMember(Integer id, MemberDTO memberDto) {
		try {
			projectService.addMember(id, memberDto.toDomain());
			Project p = projectRepository.findById(id);
			List<String> requirements = p.getRequirements().stream()
					.map(r -> r.getName())
					.collect(Collectors.toList());
			ObjectMapper objectMapper = new ObjectMapper();
			String payload = objectMapper.writeValueAsString(memberDto) + "\n" + objectMapper.writeValueAsString(requirements);
			amqpTemplate.convertAndSend(config.getExchangeName(), "member.added", payload);
			return "Member successfully added.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		} catch (JsonProcessingException e) {
			return "Member added. Message could not be send. Cause: " + e.getMessage();
		}
	}

	@Override
	public String unassignMember(Integer id, MemberDTO memberDto) {
		try {
			projectService.removeMember(id, memberDto.toDomain().getMemberId());
			return "Member successfully removed.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}
}
