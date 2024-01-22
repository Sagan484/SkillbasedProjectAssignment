package com.fse.membermanagement.infrastructure.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fse.membermanagement.domain.aggregates.member.Skill;
import com.fse.membermanagement.infrastructure.dtos.SkillDTO;

public class SkillToSkillDTOMapper {

	public List<SkillDTO> map(Set<Skill> skills) {
		List<SkillDTO> skillDTOs = new ArrayList<>();
		if(!skills.isEmpty()) {
			skillDTOs = skills.stream()
					.map(s -> new SkillDTO(s.getName(), s.getArea()))
					.collect(Collectors.toList());
		}
		return skillDTOs;
	}
}
