package com.fse.skillmanagement.application;

import java.util.List;
import java.util.Set;

public interface SkillManagementService {
	public Integer create(String name, Set<SkillDTO> skillDTOs);
	public List<SkillDTO> read(Integer id);
	public List<SkillDTO> readAll();
	public boolean delete(Integer id);
	
	public boolean addSkillToMember(Integer id, SkillDTO skillDTO);
	public boolean removeSkillFromMember(Integer id, SkillDTO skillDTO);
	public boolean changeMemberName(MemberDTO member);
}
