package com.fse.membermanagement.infrastructure.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.infrastructure.dtos.MemberDTO;

public class MemberToMemberDTOMapper {

	public Set<MemberDTO> map(Set<Member> members) {
		Set<MemberDTO> memberDTOs = new HashSet<>();
		memberDTOs = members.stream()
				.map(m -> new MemberDTO(m.getMemberId().getId(), m.getName()))
				.collect(Collectors.toSet());
		return memberDTOs;
	}
	
}
