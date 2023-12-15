package com.fse.projectmanagement;

public class ProjectEntityRepository implements ProjectRepository {

	private final JdbcProjectEntityRepository jdbcProjectEntityRepository;
	
	public ProjectEntityRepository(JdbcProjectEntityRepository jdbcProjectEntityRepository) {
		this.jdbcProjectEntityRepository = jdbcProjectEntityRepository;
	}
	
	@Override
	public Project findById(ProjectId projectId) {
		ProjectEntity projectEntity = jdbcProjectEntityRepository.findById(projectId.getId()).orElse(null);
        return projectEntity.toDomain();
	}

	@Override
	public void save(Project project) {
		jdbcProjectEntityRepository.save(new ProjectEntity(project));
	}

	@Override
	public void delete(Project project) {
		jdbcProjectEntityRepository.delete(new ProjectEntity(project));
		
	}

}
