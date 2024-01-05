package com.fse.projectmanagement.domain.repositories;

import com.fse.projectmanagement.domain.aggregates.project.Member;

public interface MemberRepository {
	
	public Member findById(Integer id);
	
	public Integer save(Member member);
}
