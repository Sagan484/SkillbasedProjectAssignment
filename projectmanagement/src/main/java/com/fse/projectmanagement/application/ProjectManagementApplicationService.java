package com.fse.projectmanagement.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.domain.services.ProjectService;

public class ProjectManagementApplicationService implements ProjectManagementService {

	private ProjectRepository projectRepository;
	private ProjectService projectService;
	private MemberToMemberDTOMapper memberMapper;
	
	public ProjectManagementApplicationService(ProjectRepository projectRepository, ProjectService projectService, MemberToMemberDTOMapper memberMapper) {
		this.projectRepository = projectRepository;
		this.projectService = projectService;
		this.memberMapper = memberMapper;
	}
	
	@Override
	public Integer create(String name, Set<MemberDTO> mList) {
		Set<Member> members = mList.stream()
				.map(m -> m.toDomain())
				.collect(Collectors.toSet());
		// DTO erstellen und übergeben und im repository zu Project?
        final Project p = new Project(new ProjectId(null), name, members);
        return projectRepository.save(p);

	}

	@Override
	public ProjectDTO read(Integer id) {
		try {
		Project p = projectRepository.findById(id);
		Set<MemberDTO> memberDTOs = memberMapper.map(p.getMembers());
		return new ProjectDTO(id, p.getName(), memberDTOs);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	@Override
	public List<ProjectDTO> readAll() {
		return 	projectRepository.findAll().stream()
	            .map(p -> new ProjectDTO(p.getProjectId().getId(), p.getName(), memberMapper.map(p.getMembers())))
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
			Project p = projectRepository.findById(id);
			projectRepository.delete(p);
			return "Project with id " + id + " successfully deleted.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}

	@Override
	public String assignMember(Integer id, MemberDTO memberDto) {
		try {
			projectService.addMember(id, memberDto.toDomain());
			return "Member successfully added.";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
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
