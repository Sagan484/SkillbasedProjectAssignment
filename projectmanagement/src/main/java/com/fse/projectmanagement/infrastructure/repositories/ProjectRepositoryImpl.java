package com.fse.projectmanagement.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.infrastructure.entities.ProjectEntity;

public class ProjectRepositoryImpl implements ProjectRepository {

	private final JdbcProjectEntityRepository jdbcProjectEntityRepository;
	
	public ProjectRepositoryImpl(JdbcProjectEntityRepository jdbcProjectEntityRepository) {
		this.jdbcProjectEntityRepository = jdbcProjectEntityRepository;
	}
	
	@Override
	public Project findById(Integer projectId) {
		ProjectEntity projectEntity = jdbcProjectEntityRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException("No project found with id " + projectId));
		return projectEntity.toDomain();
	}
	
	@Override
	public List<Project> findAll() {
		Iterable<ProjectEntity> projectEntities = jdbcProjectEntityRepository.findAll();
		List<Project> projects = new ArrayList<>();
		projectEntities.forEach(pE -> projects.add(pE.toDomain()));
		return projects;
	}

	@Override
	public Integer save(Project project) {
		return jdbcProjectEntityRepository.save(new ProjectEntity(project)).getId();
	}

	@Override
	public void delete(Project project) {
		jdbcProjectEntityRepository.delete(new ProjectEntity(project));
		
	}
}
