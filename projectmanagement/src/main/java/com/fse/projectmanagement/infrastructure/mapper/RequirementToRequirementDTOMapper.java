package com.fse.projectmanagement.infrastructure.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.projectmanagement.domain.aggregates.project.Requirement;
import com.fse.projectmanagement.infrastructure.dtos.RequirementDTO;

public class RequirementToRequirementDTOMapper {

	public Set<RequirementDTO>map(Set<Requirement> requirements) {
		Set<RequirementDTO> requirementDTOs = new HashSet<>();
		requirementDTOs = requirements.stream()
				.map(r -> new RequirementDTO(r.getName()))
				.collect(Collectors.toSet());
		return requirementDTOs;
	}	
}
