package com.fse.skillmanagement;

public interface SkillRepository {
	
	public Skill findById(SkillId id);
	public void save(Skill skill);
	public void delete(Skill skill);
}
