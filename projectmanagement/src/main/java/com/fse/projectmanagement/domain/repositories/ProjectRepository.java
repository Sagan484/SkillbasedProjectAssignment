package com.fse.projectmanagement.domain.repositories;

import java.util.List;

import com.fse.projectmanagement.domain.aggregates.project.Project;

public interface ProjectRepository {

	public Project findById(Integer id);
	public List<Project> findAll();

	public Integer save(Project project);
	public void delete(Project project);
	
}
