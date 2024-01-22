package com.fse.skillmanagement.application;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.skillmanagement.domain.aggregates.member.Member;
import com.fse.skillmanagement.domain.aggregates.member.MemberId;
import com.fse.skillmanagement.domain.aggregates.member.Skill;
import com.fse.skillmanagement.domain.repositories.MemberRepository;
import com.fse.skillmanagement.domain.services.MemberService;

public class MemberManagementApplicationService implements MemberManagementService {

	private MemberRepository memberRepository;
	private SkillToSkillDTOMapper skillMapper;
	private MemberService memberService;
	
	public MemberManagementApplicationService(MemberRepository memberRepository, MemberService memberService, SkillToSkillDTOMapper skillMapper) {
		this.memberRepository = memberRepository;
		this.skillMapper = skillMapper;
		this.memberService = memberService;
	}

	@Override
	public Integer create(String name, Set<SkillDTO> sList) {
		Set<Skill> skills = sList.stream()
				.map(s -> s.toDomain())
				.collect(Collectors.toSet());
        Member m = new Member(new MemberId(null), name, skills);
        return memberRepository.save(m);
	}
	
	@Override
	public boolean changeMemberName(MemberDTO memberDto) {
		try {
			memberService.changeName(memberDto.getId(), memberDto.getName());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Override
	public List<SkillDTO> readSkillsFromMember(Integer id) {
		try {
		Member m = memberRepository.findById(id);
		return skillMapper.map(m.getSkills());
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	@Override
	public List<SkillDTO> readAllSkills() {
		List<SkillDTO> skills = new ArrayList<>();
		memberRepository.findAll().stream()
	            .forEach(m -> skills.addAll(skillMapper.map(m.getSkills())));
		return skills;
	}
	
	@Override
	public MemberDTO read(Integer id) {
		try {
			Member m = memberRepository.findById(id);
			Set<SkillDTO> skillDTOs = (Set<SkillDTO>) skillMapper.map(m.getSkills().stream().collect(Collectors.toSet()));
			return new MemberDTO(m.getMemberId().getId(), m.getName(), skillDTOs);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Member m = memberRepository.findById(id);
			memberRepository.delete(m);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Override
	public boolean addSkillToMember(Integer id, SkillDTO skillDTO) {
		try {
			memberService.addSkill(id, skillDTO.toDomain());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Override
	public boolean removeSkillFromMember(Integer id, SkillDTO skillDTO) {
		try {
			memberService.removeSkill(id, skillDTO.toDomain());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}