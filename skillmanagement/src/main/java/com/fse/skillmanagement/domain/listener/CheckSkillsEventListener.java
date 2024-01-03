package com.fse.skillmanagement.domain.listener;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.AmqpTemplate;
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
	private AmqpTemplate amqpTemplate;
	
	private final String QUEUE_CHECK_SKILLS = "checkSkills"; //config.getQueueRemoveMembersName();

	public CheckSkillsEventListener (MemberService memberService, PropertiesConfig config, AmqpTemplate amqpTemplate) {
		this.memberService = memberService;
		this.config = config;
		this.amqpTemplate = amqpTemplate;
	}
	
	// spring boot annotation in domaine?
	 @RabbitListener(queues = QUEUE_CHECK_SKILLS)
	 public void listenRemoveMemberQueue(String payload) {
		 System.out.println("DEBUG Payload: " + payload);
			try {
				List<String> payloadList = payload.lines().collect(Collectors.toList());;
				ObjectMapper objectMapper = new ObjectMapper();
				MemberDTO memberDTO = objectMapper.readValue(payloadList.getFirst(), MemberDTO.class);
				List<String> requirements = objectMapper.readValue(payloadList.getLast(), new TypeReference<List<String>>(){});
				Boolean areRequirementsValid = memberService.checkSkills(memberDTO.getId(), requirements);
				payload = objectMapper.writeValueAsString(areRequirementsValid);
				amqpTemplate.convertAndSend(config.getExchangeName(), "skills.checked", payload);
			} catch (JsonProcessingException e) {
				// die folgende Meldung gehört eigentlich in ein Log.
				System.out.println("Interner Fehler bei der Eventverarbeitung");
				e.printStackTrace();
			}
		}
	}