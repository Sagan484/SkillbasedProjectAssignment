package com.fse.skillmanagement;

public interface SkillManagementService {
	public boolean update(int id, String name, String area);
	public Skill read(int id);
	
	public boolean assignSkill(int id, SkillTO skillTO);
	public boolean unassignSkill(int id, SkillTO skillTO);
}
