package com.fse.skillmanagement.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.skillmanagement.application.MemberDTO;
import com.fse.skillmanagement.domain.aggregates.member.Member;

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
