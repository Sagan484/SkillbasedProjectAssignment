package com.fse.projectmanagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// curl -X PATCH http://localhost:8090/pm/project/1001/addMember -H "Content-Type:application/json" -d "{\"id\":9001}"
	@PatchMapping(value = "/project/{id}/addMember", consumes = {"application/json"})
	public boolean assignMemberToProject(@PathVariable int id, @RequestBody MemberId mId) {
		return projectManagementService.assignMember(id, mId);
	}
	
	@PatchMapping(value = "/project/{id}/removeMember", consumes = {"application/json"})
	public boolean removeMemberFromProject(@PathVariable int id, @RequestBody MemberId mId) {
		return projectManagementService.unassignMember(id, mId);
	}
}
