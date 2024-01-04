package com.fse.skillmanagement.domain.listener;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.skillmanagement.application.MemberDTO;
import com.fse.skillmanagement.domain.services.MemberService;
import com.fse.skillmanagement.infrastructure.config.PropertiesConfig;


public class CheckSkillsEventListener {

	private MemberService memberService;
	private PropertiesConfig config;
	
	//TODO fix me
	private final String QUEUE_CHECK_SKILLS = "checkSkills"; //config.getQueueRemoveMembersName();

	public CheckSkillsEventListener (MemberService memberService, PropertiesConfig config) {
		this.memberService = memberService;
		this.config = config;
	}
	
	// spring boot annotation in domaine?
	 @RabbitListener(queues = QUEUE_CHECK_SKILLS)
		public String listenAndRespondManageMemberQueue(String payload) {
			System.out.println("DEBUG Payload: " + payload);
			String result = "false";
			try {
				List<String> payloadList = payload.lines().collect(Collectors.toList());
				ObjectMapper objectMapper = new ObjectMapper();
				MemberDTO memberDTO = objectMapper.readValue(payloadList.getFirst(), MemberDTO.class);
				List<String> requirements = objectMapper.readValue(payloadList.getLast(),
						new TypeReference<List<String>>() {
						});
				result = String.valueOf(memberService.checkSkills(memberDTO.getId(), requirements));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return result;
			} catch (NoSuchElementException e) {
				return e.getMessage();
			}
			return result;
		}
	}