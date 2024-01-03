package com.fse.projectmanagement.application;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DTOtoDomainMapper {

	public <T, G extends DTO<T>> Set<T> map(Set<G> dtos) {
		Set<T> domainObjects = new HashSet<T>();
		if (dtos != null) {
				domainObjects = dtos.stream()
					.map(dto -> dto.toDomain())
					.collect(Collectors.toSet());
		}
		return domainObjects;
	}
}
