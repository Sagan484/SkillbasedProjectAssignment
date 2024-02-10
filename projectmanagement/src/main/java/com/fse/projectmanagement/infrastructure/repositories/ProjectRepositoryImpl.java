package com.fse.projectmanagement.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.domain.aggregates.project.ProjectId;
import com.fse.projectmanagement.domain.repositories.ProjectRepository;
import com.fse.projectmanagement.infrastructure.entities.ProjectEntity;

public class ProjectRepositoryImpl implements ProjectRepository {

	private final JdbcProjectEntityRepository jdbcProjectEntityRepository;
	
	public ProjectRepositoryImpl(JdbcProjectEntityRepository jdbcProjectEntityRepository) {
		this.jdbcProjectEntityRepository = jdbcProjectEntityRepository;
	}
	
	@Override
	public Project findById(ProjectId projectId) {
		ProjectEntity projectEntity = jdbcProjectEntityRepository.findById(projectId.getId()).orElseThrow(() -> new NoSuchElementException("No project found with id " + projectId));
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
		try {
			return jdbcProjectEntityRepository.save(new ProjectEntity(project)).getId();
		} catch (DbActionExecutionException e) {
			return -1;
		}
	}

	@Override
	public void delete(Project project) {
		jdbcProjectEntityRepository.delete(new ProjectEntity(project));	
	}
}
