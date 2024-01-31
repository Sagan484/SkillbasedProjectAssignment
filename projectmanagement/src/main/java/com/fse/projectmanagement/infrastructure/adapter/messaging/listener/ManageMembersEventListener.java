package com.fse.projectmanagement.infrastructure.adapter.messaging.listener;

import java.util.NoSuchElementException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.projectmanagement.domain.services.MemberService;
import com.fse.projectmanagement.infrastructure.dtos.MemberDTO;


public class ManageMembersEventListener {

	private MemberService memberService;

	public ManageMembersEventListener (MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.manage.members.name}")
	public void listenRabbit(String payload) {
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

	@KafkaListener(topics = "${kafka.topic.name.listener}")
	public void listenKafka(String payload) {
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