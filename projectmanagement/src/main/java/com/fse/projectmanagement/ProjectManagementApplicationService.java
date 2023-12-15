package com.fse.projectmanagement;

public class ProjectManagementApplicationService implements ProjectManagementService {

	private ProjectRepository projectRepository;
	private MemberRepository memberRepository;
	
	public ProjectManagementApplicationService(ProjectRepository projectRepository, MemberRepository memberRepository) {
		this.projectRepository = projectRepository;
		this.memberRepository = memberRepository;
	}
	
	@Override
	public boolean update(int id, String name) {
		Project project = projectRepository.findById(new ProjectId(id));
		if (project != null) {
			project.changeName(name);
			projectRepository.save(project);
			return true;
		}
		return false;
	}

	@Override
	public Project read(int id) {
		return projectRepository.findById(new ProjectId(id));
	}

	@Override
	public boolean delete(int id) {
		Project project = projectRepository.findById(new ProjectId(id));
		if (project != null) {
			projectRepository.delete(project);
			return true;
		}
		return false;
	}

	@Override
	public boolean assignMember(int id, int mId) {
		Project project = projectRepository.findById(new ProjectId(id));
		Member member = memberRepository.findById(new MemberId(id));
		if (project != null && member != null) {
			project.addMember(member);
			projectRepository.save(project);
			return true;
		}
		return false;
	}

	@Override
	public boolean unassignMember(int id, int mId) {
		Project project = projectRepository.findById(new ProjectId(id));
		Member member = memberRepository.findById(new MemberId(id));
		if (project != null && member != null) {
			project.removeMember(member);
			projectRepository.save(project);
			return true;
		}
		return false;
	}
}
