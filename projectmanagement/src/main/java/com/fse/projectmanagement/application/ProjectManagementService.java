package com.fse.projectmanagement.application;

import java.util.List;
import java.util.Set;

public interface ProjectManagementService {
	public Integer create(String name, Set<MemberDTO> memberDtos);
	public ProjectDTO read(Integer id);
	public List<ProjectDTO> readAll();
	public boolean changeProjectName(Integer id, String name);
	public boolean delete(Integer id);
	
	public boolean assignMember(Integer id, MemberDTO memberDto);
	public boolean unassignMember(Integer id, MemberDTO memberDto);
}
