package com.fse.projectmanagement.application;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.projectmanagement.domain.aggregates.project.Member;

public class MemberToMemberDTOMapper {

	public Set<MemberDTO> map(Set<Member> members) {
		Set<MemberDTO> memberDTOs = new HashSet<>();
		memberDTOs = members.stream()
				.map(m -> new MemberDTO(m.getMemberId().getId(), m.getName()))
				.collect(Collectors.toSet());
		return memberDTOs;
	}
	
}
