package com.fse.skillmanagement.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;

import com.fse.skillmanagement.domain.aggregates.member.Member;
import com.fse.skillmanagement.domain.repositories.MemberRepository;
import com.fse.skillmanagement.infrastructure.entities.MemberEntity;

public class MemberRepositoryImpl implements MemberRepository {

	private final JdbcMemberEntityRepository jdbcMemberEntityRepository;
	
	public MemberRepositoryImpl(JdbcMemberEntityRepository jdbcMemberEntityRepository) {
		this.jdbcMemberEntityRepository = jdbcMemberEntityRepository;
	}
	
	@Override
	public Member findById(Integer memberId) {
		MemberEntity memberEntity = jdbcMemberEntityRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("No member found with id " + memberId));
		return memberEntity.toDomain();
	}
	
	@Override
	public List<Member> findAll() {
		Iterable<MemberEntity> memberEntities = jdbcMemberEntityRepository.findAll();
		List<Member> members = new ArrayList<>();
		memberEntities.forEach(mE -> members.add(mE.toDomain()));
		return members;
	}

	@Override
	public Integer save(Member member) {
		return jdbcMemberEntityRepository.save(new MemberEntity(member)).getId();
	}

	@Override
	public void delete(Member member) {
		jdbcMemberEntityRepository.delete(new MemberEntity(member));
		
	}
}
