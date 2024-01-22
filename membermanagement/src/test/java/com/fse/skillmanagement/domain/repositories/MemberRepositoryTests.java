package com.fse.skillmanagement.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.fse.membermanagement.domain.aggregates.member.Member;
import com.fse.membermanagement.domain.aggregates.member.MemberId;
import com.fse.membermanagement.domain.aggregates.member.Skill;
import com.fse.membermanagement.domain.repositories.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableJdbcRepositories
class MemberRepositoryTests {
	
	@Autowired
	private MemberRepository memberRepository;
	
	private Set<Skill> skills = new HashSet<>();
	Skill skill = new Skill("TestSkill", "TestArea");
	

	
	@Test
	@Transactional
	void testIfMemberWithSameIDIsUpdated() {
		Member member1 = new Member (new MemberId(null), "Test Member", skills);
		Integer memberId1 = memberRepository.save(member1);
		Member member2 = new Member(new MemberId(memberId1), "Test Member", skills);
		Integer memberId2 = memberRepository.save(member2);
		assertEquals(memberId1, memberId2);
	}
}
