package com.fse.membermanagement.domain.services;

import java.util.List;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.Skill;
import com.fse.membermanagement.domain.events.MemberDataChangedEvent;
import com.fse.membermanagement.domain.repositories.MemberRepository;
import com.fse.membermanagement.infrastructure.adapter.messaging.MessagingService;

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
		messagingService.sendViaRabbit(new MemberDataChangedEvent(member));
		// messagingService.sendViaKafka(new MemberDataChangedEvent(member));
	}
}
