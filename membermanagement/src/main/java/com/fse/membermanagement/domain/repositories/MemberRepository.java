package com.fse.membermanagement.domain.repositories;

import java.util.List;

import com.fse.membermanagement.domain.aggregates.member.Member;

public interface MemberRepository {

	public Member findById(Integer id);
	public List<Member> findAll();
	public Integer save(Member member);
	public void delete(Member member);
}
