package com.fse.projectmanagement.domain.listener;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.projectmanagement.application.MemberDTO;
import com.fse.projectmanagement.domain.services.MemberService;
import com.fse.projectmanagement.infrastructure.config.PropertiesConfig;


public class ManageMembersEventListener {

	private MemberService memberService;
	private PropertiesConfig config;
	
	//TODO fix me
	private final String QUEUE_MANAGE_MEMBERS = "manageMembers"; //config.getQueueRemoveMembersName();

	public ManageMembersEventListener (MemberService memberService, PropertiesConfig config) {
		this.memberService = memberService;
		this.config = config;
	}
	
	// spring boot annotation in domaine?
	 @RabbitListener(queues = QUEUE_MANAGE_MEMBERS)
		public void listen(String payload) {
			System.out.println("DEBUG Payload: " + payload);
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				MemberDTO memberDTO = objectMapper.readValue(payload, MemberDTO.class);
				memberService.changeMemberData(memberDTO.toDomain());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				System.out.println("Member not found. Probably the member is not assigned to any project.");
			}
	}
}