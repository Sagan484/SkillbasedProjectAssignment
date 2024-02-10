package com.fse.membermanagement.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.MemberId;
import com.fse.membermanagement.domain.aggregates.member.Skill;
import com.fse.membermanagement.domain.repositories.MemberRepository;
import com.fse.membermanagement.domain.services.MemberService;
import com.fse.membermanagement.infrastructure.dtos.MemberDTO;
import com.fse.membermanagement.infrastructure.dtos.SkillDTO;
import com.fse.membermanagement.infrastructure.mapper.SkillToSkillDTOMapper;

public class MemberManagementApplicationServiceImpl implements MemberManagementApplicationService {

	private MemberRepository memberRepository;
	private SkillToSkillDTOMapper skillMapper;
	private MemberService memberService;
	
	public MemberManagementApplicationServiceImpl(MemberRepository memberRepository, MemberService memberService, SkillToSkillDTOMapper skillMapper) {
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
		Member m = memberRepository.findById(new MemberId(id));
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
			Member m = memberRepository.findById(new MemberId(id));
			List<SkillDTO> skillList = skillMapper.map(m.getSkills());
			Set<SkillDTO> skills = (HashSet<SkillDTO>)skillList.stream().collect(Collectors.toSet());
			return new MemberDTO(m.getMemberId().getId(), m.getName(), skills);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public boolean delete(Integer id) {
		try {
			Member m = memberRepository.findById(new MemberId(id));
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