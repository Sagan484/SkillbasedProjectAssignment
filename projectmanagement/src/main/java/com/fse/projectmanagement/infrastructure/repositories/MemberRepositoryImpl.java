package com.fse.projectmanagement.infrastructure.repositories;

import java.util.NoSuchElementException;

import com.fse.projectmanagement.domain.aggregates.project.Member;
import com.fse.projectmanagement.domain.repositories.MemberRepository;
import com.fse.projectmanagement.infrastructure.entities.MemberEntity;

public class MemberRepositoryImpl implements MemberRepository {

	private final JdbcMemberEntityRepository jdbcMemberEntityRepository;
	
	public MemberRepositoryImpl(JdbcMemberEntityRepository jdbcMemberEntityRepository) {
		this.jdbcMemberEntityRepository = jdbcMemberEntityRepository;
	}
	
	@Override
	public Member findById(Integer memberId) {
		MemberEntity memberEntity = jdbcMemberEntityRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("No member found with id " + memberId));
		return memberEntity.toDomain();
	}
	
	@Override
	public Integer save(Member member) {
		return jdbcMemberEntityRepository.save(new MemberEntity(member)).getId();
	}
}
