package com.fse.projectmanagement;

import java.util.Optional;

public class MemberEntityRepository implements MemberRepository {

	private final JdbcMemberEntityRepository jdbcMemberEntityRepository;
	
	public MemberEntityRepository(JdbcMemberEntityRepository jdbcMemberEntityRepository) {
		this.jdbcMemberEntityRepository = jdbcMemberEntityRepository;
	}

	@Override
	public Member findById(MemberId memberId) {
		Optional<MemberEntity> memberEntity = jdbcMemberEntityRepository.findById(memberId.getId());
        if (memberEntity.isPresent()) {
            return memberEntity.get().toDomain();
        } else {
            return null;
        }
	}

	@Override
	public void save(Member member) {
		jdbcMemberEntityRepository.save(new MemberEntity(member));
	}

	@Override
	public void delete(Member member) {
		jdbcMemberEntityRepository.delete(new MemberEntity(member));
	}
}
