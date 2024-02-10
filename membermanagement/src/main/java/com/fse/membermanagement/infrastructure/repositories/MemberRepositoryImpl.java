package com.fse.membermanagement.infrastructure.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.MemberId;
import com.fse.membermanagement.domain.repositories.MemberRepository;
import com.fse.membermanagement.infrastructure.entities.MemberEntity;

public class MemberRepositoryImpl implements MemberRepository {

	private final JdbcMemberEntityRepository jdbcMemberEntityRepository;
	
	public MemberRepositoryImpl(JdbcMemberEntityRepository jdbcMemberEntityRepository) {
		this.jdbcMemberEntityRepository = jdbcMemberEntityRepository;
	}
	
	@Override
	public Member findById(MemberId memberId) {
		MemberEntity memberEntity = jdbcMemberEntityRepository.findById(memberId.getId()).orElseThrow(() -> new NoSuchElementException("No member found with id " + memberId));
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
