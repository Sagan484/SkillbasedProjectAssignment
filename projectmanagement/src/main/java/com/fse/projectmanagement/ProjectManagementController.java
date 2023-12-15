package com.fse.projectmanagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pm")
public class ProjectManagementController {
	
	private ProjectManagementService projectManagementService;
	
	public ProjectManagementController (ProjectManagementService projectManagementService) {
		this.projectManagementService = projectManagementService;
	}
	
	// curl -X GET http://localhost:8090/pm/project/{id}
	@GetMapping("/project/{id}")
	public String getProjectInformation(@PathVariable int id) {	
		Project p = projectManagementService.read(id);
		if (p != null) {
			return p.toString();
		}
		return "No project found with ID: " + id;
	}
}
