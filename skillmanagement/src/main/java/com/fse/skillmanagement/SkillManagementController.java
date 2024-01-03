package com.fse.skillmanagement;

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

import com.fse.skillmanagement.application.MemberDTO;
import com.fse.skillmanagement.application.SkillDTO;
import com.fse.skillmanagement.application.SkillManagementService;

@RestController
@RequestMapping("/sm")
public class SkillManagementController {
	
private SkillManagementService skillManagementService;
	
	public SkillManagementController (SkillManagementService skillManagementService) {
		this.skillManagementService = skillManagementService;
	}
	
	// curl -X POST http://localhost:8091/sm/member -H "Content-Type:application/json" -d "{\"name\":\"Schmitz\",\"skills\":[{\"name\":\"Java\", \"area\":\"Programmierung\", ,\"certificates\":[{\"name\":\"Java-Zertifikat 1\", \"name\":\"Java-Zertifikat 2\"}]]}"
	@PostMapping(value = "/member", consumes = {"application/json"})
	public String createMember(@RequestBody MemberDTO member) {
		Integer id = skillManagementService.create(member.getName(), member.getSkills());
		return "Member created with id " + id;
	}
	
	// curl -X GET http://localhost:8091/sm/skill/{id}
	@GetMapping(value = "/skill/{id}")
	public String getSkillInformationOfMember(@PathVariable Integer id) {	
		List<SkillDTO> skills = skillManagementService.read(id);
		if (skills != null) {
			if (!skills.isEmpty()) {
				return skills.toString();
			}
			return "No skills found for member with ID: " + id;
		}
		return "No member found with ID: " + id;
	}
	
	// curl -X GET http://localhost:8091/sm/skill
	@GetMapping(value = "/skill")
	public List<String> getAllSkillsInformation() {	
		List<SkillDTO> sList = skillManagementService.readAll();
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
	
	// curl -X PATCH http://localhost:8091/sm/member/1064/addSkill -H "Content-Type:application/json" -d "{\"name\":\"Java\", \"area\":\"Programmierung\", \"certificates\":[{\"name\":\"Java Zertifikat 1\", \"name\":\"Java Zertifikat 2\"}]}"
	@PatchMapping(value = "/member/{id}/addSkill", consumes = {"application/json"})
	public String addSkillToMember(@PathVariable Integer id, @RequestBody SkillDTO skill) {
		if (skillManagementService.addSkillToMember(id, skill)) {
			return "Skill successfully added.";
		}
		return "No member found with ID: " + id;
	}
	
	// curl -X PATCH http://localhost:8091/sm/member/1064/removeSkill -H "Content-Type:application/json" -d "{\"name\":\"Java\", \"area\":\"Programmierung\"}"
	@PatchMapping(value = "/member/{id}/removeSkill", consumes = {"application/json"})
	public String removeSkillFromMember(@PathVariable Integer id, @RequestBody SkillDTO skill) {
		if (skillManagementService.removeSkillFromMember(id, skill)) {
			return "Skill successfully removed.";
		}
		return "No member found with ID: " + id;
	}
}
