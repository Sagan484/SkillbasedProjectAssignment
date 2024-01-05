package com.fse.projectmanagement.domain.services;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.repositories.MemberRepository;

public class MemberService {

	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void changeMemberData(Member newMember) {
		Member member = memberRepository.findById(newMember.getMemberId().getId());
		member.changeName(newMember.getName());
		memberRepository.save(member);
	}
}
