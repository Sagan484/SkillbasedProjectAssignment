package com.fse.membermanagement.infrastructure.adapter.listener;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.membermanagement.domain.services.MemberService;
import com.fse.membermanagement.infrastructure.config.PropertiesConfig;
import com.fse.membermanagement.infrastructure.dtos.MemberDTO;


public class CheckSkillsEventListener {

	private MemberService memberService;
	private PropertiesConfig config;
	
	//TODO fix me
	private final String QUEUE_CHECK_SKILLS = "checkSkills"; //config.getQueueRemoveMembersName();

	public CheckSkillsEventListener (MemberService memberService, PropertiesConfig config) {
		this.memberService = memberService;
		this.config = config;
	}

	@RabbitListener(queues = QUEUE_CHECK_SKILLS)
	public String listenAndRespondRabbit(String payload) {
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
	
	@KafkaListener(topics = "${kafka.topic.name.listener}")
	@SendTo
	public String listenAndRespondKafka(String payload) {
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