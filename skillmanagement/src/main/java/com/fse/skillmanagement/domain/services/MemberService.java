package com.fse.skillmanagement.domain.services;

import java.util.List;

import com.fse.skillmanagement.adapter.messaging.MessagingService;
import com.fse.skillmanagement.domain.aggregates.member.Member;
import com.fse.skillmanagement.domain.aggregates.member.Skill;
import com.fse.skillmanagement.domain.events.MemberDataChangedEvent;
import com.fse.skillmanagement.domain.repositories.MemberRepository;

public class MemberService {
	
	private MemberRepository memberRepository;
	private MessagingService messagingService;
	
	public MemberService(MemberRepository memberRepository, MessagingService messagingService) {
		this.memberRepository = memberRepository;
		this.messagingService = messagingService;
	}
	
	public void addSkill(Integer id, Skill skill) {
		Member member = memberRepository.findById(id);
		member.addSkill(skill);
		memberRepository.save(member);
	}
	
	public void removeSkill(Integer id, Skill skill) {
		Member member = memberRepository.findById(id);
		member.removeSkill(skill);
		memberRepository.save(member);
	}
	
	public boolean checkSkills(Integer id, List<String> requirements) {
		Member member = memberRepository.findById(id);
		return member.areSkillsValid(requirements);
	}

	public void remove(Integer id) {
		Member member = memberRepository.findById(id);
		memberRepository.delete(member);
	}

	public void changeName(Integer id, String name) {
		Member member = memberRepository.findById(id);
		member.changeName(name);
		memberRepository.save(member);
		messagingService.send(new MemberDataChangedEvent(member));
	}
}
