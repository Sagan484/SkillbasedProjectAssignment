package com.fse.projectmanagement.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.aggregates.project.Requirement;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.domain.services.ProjectService;

public class ProjectManagementApplicationService implements ProjectManagementService {

	private ProjectRepository projectRepository;
	private ProjectService projectService;
	private MemberToMemberDTOMapper memberMapper;
	private RequirementToRequirementDTOMapper requirementMapper;
	private DTOtoDomainMapper dtoToDomainMapper;
		
	public ProjectManagementApplicationService(ProjectRepository projectRepository,
			ProjectService projectService,
			MemberToMemberDTOMapper memberMapper,
			RequirementToRequirementDTOMapper requirementMapper,
			DTOtoDomainMapper dtoToDomainMapper) {
		this.projectRepository = projectRepository;
		this.projectService = projectService;
		this.memberMapper = memberMapper;
		this.requirementMapper = requirementMapper;
		this.dtoToDomainMapper = dtoToDomainMapper;
	}
	
	@Override
	public Integer create(String name, Set<RequirementDTO> rSet) {
		Set<Requirement> requirements = dtoToDomainMapper.map(rSet);
        Project p = new Project(new ProjectId(null), name, requirements);
        return projectRepository.save(p);
	}

	@Override
	public ProjectDTO read(Integer id) {
		try {
		Project p = projectRepository.findById(id);
		Set<MemberDTO> memberDTOs = memberMapper.map(p.getMembers());
		Set<RequirementDTO> requirementDTOs = requirementMapper.map(p.getRequirements());
		return new ProjectDTO(id, p.getName(), memberDTOs, requirementDTOs);
		} catch (NoSuchElementException e) {
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
		} catch (NoSuchElementException e) {
			return e.getMessage();
		}
	}

	@Override
	public String delete(Integer id) {
		try {
			Project project = projectRepository.findById(id);
			projectRepository.delete(project);
			return "Project with id " + id + " successfully deleted.";
		} catch (NoSuchElementException e) {
			return e.getMessage();
		}
	}

	@Override
	public String assignMember(Integer id, MemberDTO memberDto) {
		return projectService.addMember(id, memberDto.toDomain());
	}

	@Override
	public String unassignMember(Integer id, MemberDTO memberDto) {
		try {
			projectService.removeMember(id, memberDto.toDomain().getMemberId());
			return "Member successfully removed.";
		} catch (NoSuchElementException e) {
			return e.getMessage();
		}
	}
}
