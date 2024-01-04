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

public class SkillManagementApplicationService implements SkillManagementService {

	private MemberRepository memberRepository;
	private SkillToSkillDTOMapper skillMapper;
	private MemberService memberService;
	
	public SkillManagementApplicationService(MemberRepository memberRepository, MemberService memberService, SkillToSkillDTOMapper skillMapper) {
		this.memberRepository = memberRepository;
		this.skillMapper = skillMapper;
		this.memberService = memberService;
	}

	@Override
	public Integer create(String name, Set<SkillDTO> sList) {
		Set<Skill> skills = sList.stream()
				.map(s -> s.toDomain())
				.collect(Collectors.toSet());
		// DTO erstellen und übergeben und im repository zu Member?
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
	public List<SkillDTO> read(Integer id) {
		try {
		Member m = memberRepository.findById(id);
		return skillMapper.map(m.getSkills());
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	@Override
	public List<SkillDTO> readAll() {
		List<SkillDTO> skills = new ArrayList<>();
		memberRepository.findAll().stream()
	            .forEach(m -> skills.addAll(skillMapper.map(m.getSkills())));
		return skills;
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