package com.fse.projectmanagement.application;

import java.util.List;
import java.util.Set;

import com.fse.projectmanagement.infrastructure.dtos.MemberDTO;
import com.fse.projectmanagement.infrastructure.dtos.ProjectDTO;
import com.fse.projectmanagement.infrastructure.dtos.RequirementDTO;

public interface ProjectManagementService {
	public Integer create(String name, Set<RequirementDTO> requirementDtos);
	public ProjectDTO read(Integer id);
	public List<ProjectDTO> readAll();
	public String changeProjectName(Integer id, String name);
	public String delete(Integer id);
	
	public String assignMember(Integer id, MemberDTO memberDto);
	public String unassignMember(Integer id, MemberDTO memberDto);
}
