package com.fse.skillmanagement;

public interface MemberRepository {

	public Member findById(MemberId memberId);
	public void save(Member member);
	public void delete(Member member);
}
