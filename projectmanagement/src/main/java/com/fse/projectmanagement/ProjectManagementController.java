package com.fse.projectmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.projectmanagement.application.MemberDTO;
import com.fse.projectmanagement.application.ProjectDTO;
import com.fse.projectmanagement.application.ProjectManagementService;



@RestController
@RequestMapping("/pm")
public class ProjectManagementController {
	
	private ProjectManagementService projectManagementService;
	
	public ProjectManagementController (ProjectManagementService projectManagementService) {
		this.projectManagementService = projectManagementService;
	}
	
	// curl -X POST http://localhost:8090/pm/project -H "Content-Type:application/json" -d "{\"name\":\"Projekt 2\",\"members\":[{\"name\":\"Becker\"}],\"requirements\":[{\"name\":\"Java\"}, {\"name\":\"Oracle\"}, {\"name\":\"Englisch\"}]}"
	@PostMapping(value = "/project", consumes = {"application/json"})
	public String createProject(@RequestBody ProjectDTO project) {
		Integer id = projectManagementService.create(project.getName(), project.getMembers(), project.getRequirements());
		return "Project created with id " + id;
	}
	
	// curl -X GET http://localhost:8090/pm/project/{id}
	@GetMapping(value = "/project/{id}")
	public String getProjectInformation(@PathVariable Integer id) {	
		ProjectDTO p = projectManagementService.read(id);
		if (p != null) {
			return p.toString();
		}
		return "No project found with ID: " + id;
	}
	
	// curl -X GET http://localhost:8090/pm/project
	@GetMapping(value = "/project")
	public List<String> getAllProjectsInformation() {	
		List<ProjectDTO> pList = projectManagementService.readAll();
		List<String> projects = new ArrayList<>();
		if (!pList.isEmpty()) {
			pList.stream()
		            .map(p -> projects.add(p.toString() + "\n"))
		            .collect(Collectors.toList());
		} else {
			projects.add("No projects found.");
		}
		return projects;
	}
	
	// curl -X PATCH http://localhost:8090/pm/project/{id} -H "Content-Type:application/json" -d "{\"name\":\"neuer Projektname\"}"
	@PatchMapping(value = "/project/{id}", consumes = {"application/json"})
	public String updateProjectName(@PathVariable Integer id, @RequestBody ProjectDTO project) {	
		return projectManagementService.changeProjectName(id, project.getName());
	}
	
	// curl -X DELETE http://localhost:8090/pm/project/{id}
	@DeleteMapping(value = "/project/{id}")
	public String deleteProject(@PathVariable Integer id) {	
		return projectManagementService.delete(id);
	}
	
	// curl -X PATCH http://localhost:8090/pm/project/1064/addMember -H "Content-Type:application/json" -d "{\"id\":9064, \"name\":\"Schmitz\"}"
	@PatchMapping(value = "/project/{id}/addMember", consumes = {"application/json"})
	public String assignMemberToProject(@PathVariable Integer id, @RequestBody MemberDTO member) {
		return projectManagementService.assignMember(id, member);
	}
	
	// curl -X PATCH http://localhost:8090/pm/project/1064/removeMember -H "Content-Type:application/json" -d "{\"id\":9064, \"name\":\"Schmitz\"}"
	@PatchMapping(value = "/project/{id}/removeMember", consumes = {"application/json"})
	public String removeMemberFromProject(@PathVariable Integer id, @RequestBody MemberDTO member) {
		return projectManagementService.unassignMember(id, member);
	}
}
