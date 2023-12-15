package com.fse.projectmanagement;

public interface ProjectRepository {

	public Project findById(ProjectId id);
	public void save(Project project);
	public void delete(Project project);
	
}
