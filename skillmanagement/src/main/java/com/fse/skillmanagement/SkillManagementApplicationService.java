package com.fse.skillmanagement;

public class SkillManagementApplicationService implements SkillManagementService {

	private SkillRepository skillRepository;
	private MemberRepository memberRepository;
	
	public SkillManagementApplicationService(SkillRepository skillRepository, MemberRepository memberRepository) {
		this.skillRepository = skillRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public boolean update(int id, String name, String area) {
		Skill skill = skillRepository.findById(new SkillId(id));
		if (skill != null) {
			skill.changeName(name);
			skill.changeArea(area);
			skillRepository.save(skill);
			return true;
		}
		return false;
	}
	

	@Override
	public Skill read(int id) {
		return skillRepository.findById(new SkillId(id));
	}

	@Override
	public boolean assignSkill(int id, SkillTO skillTO) {
		int sId = skillTO.getId();
		Skill skill = skillRepository.findById(new SkillId(sId));
		Member member = memberRepository.findById(new MemberId(id));
		if (skill != null && member != null) {
			skill.addMember(member);
		}
		return false;
	}

	@Override
	public boolean unassignSkill(int id, SkillTO skillTO) {
		// TODO Auto-generated method stub
		return false;
	}
}
