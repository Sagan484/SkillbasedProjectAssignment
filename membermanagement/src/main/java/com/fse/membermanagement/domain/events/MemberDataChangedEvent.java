package com.fse.membermanagement.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.infrastructure.dtos.MemberDTO;

public class MemberDataChangedEvent extends DomainEvent {

	public MemberDataChangedEvent(Member member) {
		MemberDTO memberDto = new MemberDTO(member.getMemberId().getId(), member.getName());
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			this.payload = objectMapper.writeValueAsString(memberDto);
		} catch (JsonProcessingException e) {
			System.out.println("Message could not be send. Cause: " + e.getMessage());
		}
	}
}
