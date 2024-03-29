package com.fse.membermanagement.infrastructure.adapter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.membermanagement.application.MemberManagementApplicationService;
import com.fse.membermanagement.infrastructure.dtos.MemberDTO;
import com.fse.membermanagement.infrastructure.dtos.SkillDTO;

@RestController
@RequestMapping("/mm")
public class MemberManagementController {
	
private MemberManagementApplicationService memberManagementApplicationService;
	
	public MemberManagementController (MemberManagementApplicationService memberManagementApplicationService) {
		this.memberManagementApplicationService = memberManagementApplicationService;
	}
	
	// curl -X POST http://localhost:8091/mm/member -H "Content-Type:application/json" -d "{\"name\":\"Schmitz\",\"skills\":[{\"name\":\"Java\", \"area\":\"Programmierung\"}]}"
	@PostMapping(value = "/member", consumes = {"application/json"})
	public String createMember(@RequestBody MemberDTO member) {
		Integer id = memberManagementApplicationService.create(member.getName(), member.getSkills());
		return "Member created with id " + id;
	}
	
	// curl -X PATCH http://localhost:8091/mm/member -H "Content-Type:application/json" -d "{\"id\":9000, \"name\":\"neuer Schmitz\"}"
	@PatchMapping(value = "/member", consumes = {"application/json"})
	public String changeMemberName(@RequestBody MemberDTO member) {
		if(memberManagementApplicationService.changeMemberName(member)) {
			return "Member name changed successfully.";
		}
		return "No member found with ID: " + member.getId();
	}
	
	// curl -X GET http://localhost:8091/mm/skill/{id}
	@GetMapping(value = "/skill/{id}")
	public String getSkillInformationOfMember(@PathVariable Integer id) {	
		List<SkillDTO> skills = memberManagementApplicationService.readSkillsFromMember(id);
		if (skills != null) {
			if (!skills.isEmpty()) {
				return skills.toString();
			}
			return "No skills found for member with ID: " + id;
		}
		return "No member found with ID: " + id;
	}
	
	// curl -X GET http://localhost:8091/mm/skill
	@GetMapping(value = "/skill")
	public List<String> getAllSkillsInformation() {	
		List<SkillDTO> sList = memberManagementApplicationService.readAllSkills();
		List<String> skills = new ArrayList<>();
		if (!sList.isEmpty()) {
			sList.stream()
		            .map(s -> skills.add(s.toString() + "\\"))
		            .collect(Collectors.toList());
		} else {
			skills.add("No skills found.");
		}
		return skills;
	}

	// curl -X GET http://localhost:8091/mm/member/{id}
	@GetMapping(value = "/member/{id}")
	public String getMemberInformation(@PathVariable Integer id) {
		MemberDTO m = memberManagementApplicationService.read(id);
		if (m != null) {
			return m.toString();
		}
		return "No member found with ID: " + id;
	}
	
	// curl -X PATCH http://localhost:8091/mm/member/1064/addSkill -H "Content-Type:application/json" -d "{\"name\":\"Java\", \"area\":\"Programmierung\"}"
	@PatchMapping(value = "/member/{id}/addSkill", consumes = {"application/json"})
	public String addSkillToMember(@PathVariable Integer id, @RequestBody SkillDTO skill) {
		if (memberManagementApplicationService.addSkillToMember(id, skill)) {
			return "Skill successfully added.";
		}
		return "No member found with ID: " + id;
	}
	
	// curl -X PATCH http://localhost:8091/mm/member/1064/removeSkill -H "Content-Type:application/json" -d "{\"name\":\"Java\", \"area\":\"Programmierung\"}"
	@PatchMapping(value = "/member/{id}/removeSkill", consumes = {"application/json"})
	public String removeSkillFromMember(@PathVariable Integer id, @RequestBody SkillDTO skill) {
		if (memberManagementApplicationService.removeSkillFromMember(id, skill)) {
			return "Skill successfully removed.";
		}
		return "No member found with ID: " + id;
	}
}
