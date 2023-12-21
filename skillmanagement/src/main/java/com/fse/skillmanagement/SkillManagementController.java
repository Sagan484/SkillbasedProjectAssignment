package com.fse.skillmanagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sm")
public class SkillManagementController {
	
	private SkillManagementService skillManagementService;
	
	public SkillManagementController (SkillManagementService skillManagementService) {
		this.skillManagementService = skillManagementService;
	}
	
	// curl -X GET http://localhost:8090/sm/skill/{id}
	@GetMapping("/skill/{id}")
	public String getSkillInformation(@PathVariable int id) {	
		Skill s = skillManagementService.read(id);
		if (s != null) {
			return s.toString();
		}
		return "No skill found with ID: " + id;
	}
	
}
