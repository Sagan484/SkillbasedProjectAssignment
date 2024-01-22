package com.fse.projectmanagement.domain.events;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.aggregates.project.Project;
import com.fse.projectmanagement.infrastructure.dtos.MemberDTO;

public class MemberTemporalyAddedEvent extends DomainEvent {

	public MemberTemporalyAddedEvent(Project project, Member member) {
		List<String> requirements = project.getRequirements().stream().map(r -> r.getName())
				.collect(Collectors.toList());
		MemberDTO memberDto = new MemberDTO(member.getMemberId().getId(), member.getName());
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			this.payload = objectMapper.writeValueAsString(memberDto) + "\n" + objectMapper.writeValueAsString(requirements);
		} catch (JsonProcessingException e) {
			System.out.println("Message could not be send. Cause: " + e.getMessage());
		}
	}
}
