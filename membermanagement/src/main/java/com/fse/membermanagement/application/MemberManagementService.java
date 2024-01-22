package com.fse.membermanagement.application;

import java.util.List;
import java.util.Set;

public interface MemberManagementService {
	public Integer create(String name, Set<SkillDTO> skillDTOs);
	public List<SkillDTO> readSkillsFromMember(Integer id);
	public List<SkillDTO> readAllSkills();
	public MemberDTO read(Integer id);
	public boolean delete(Integer id);
	
	public boolean addSkillToMember(Integer id, SkillDTO skillDTO);
	public boolean removeSkillFromMember(Integer id, SkillDTO skillDTO);
	public boolean changeMemberName(MemberDTO member);
}
