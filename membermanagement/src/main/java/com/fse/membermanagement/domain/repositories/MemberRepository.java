package com.fse.membermanagement.domain.repositories;

import java.util.List;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.MemberId;

public interface MemberRepository {

	public Member findById(MemberId id);
	public List<Member> findAll();
	public Integer save(Member member);
	public void delete(Member member);
}
