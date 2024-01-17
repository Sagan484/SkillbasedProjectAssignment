package com.fse.skillmanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fse.skillmanagement.domain.aggregates.member.Member;
import com.fse.skillmanagement.domain.aggregates.member.MemberId;
import com.fse.skillmanagement.domain.aggregates.member.Skill;
import com.fse.skillmanagement.domain.repositories.MemberRepository;
import com.fse.skillmanagement.infrastructure.entities.MemberEntity;

@SpringBootTest
class SkillmanagementApplicationTests {
	
	@Autowired
	private MemberRepository memberRepository;
	
	private Integer mId = 1000;
	private Set<Skill> skills = new HashSet<>();
	Skill skill = new Skill("TestSkill", "TestArea");
	
	@Test
	void testShouldThrowNPEIfSkillsAreNull() {
		assertThrows(NullPointerException.class,
				()-> {
					new MemberEntity(new Member(new MemberId(mId), "Test Member", null));
				});
	}
	
	@Test
	@Transactional
	void testIfMemberWithSameIDIsUpdated() {
		Member member1 = new Member (new MemberId(null), "Test Member", skills);
		Integer memberId1 = memberRepository.save(member1);
		Member member2 = new Member(new MemberId(memberId1), "Test Member", skills);
		Integer memberId2 = memberRepository.save(member2);
		assertEquals(memberId1, memberId2);
	}
	
	@Test
	void testAddingSkill() {
		Member member = new Member(new MemberId(null), "Test Member", skills);
		member.addSkill(skill);
		assertThat(member.getSkills())
				.hasSize(1)
				.extracting(Skill::getName)
				.containsExactly("TestSkill");
	}
	
	@Test
	void testRemovingSkill() {
		skills.add(skill);
		Member member = new Member(new MemberId(null), "Test Member", skills);
		member.removeSkill(skill);
		assertThat(member.getSkills()).hasSize(0);
	}
	
	@Test
	void testShouldThrowNoSuchElementFoundExceptionIfNoSkillFoundForRemovingSkill() {
		Member member = new Member(new MemberId(mId), "Test Member", skills);
		assertThrows(NoSuchElementException.class,
				()->{ member.removeSkill(skill);
				}, "Skill '" + skill.getName() + "' not assigned to member " + mId);
	}
	
	@Test
	void testSkillValidationShouldBeTrue() {
		List<String> requirements = new ArrayList<>();
		Collections.addAll(requirements, "Java", "Oracle", "Scrum");
		Set<Skill> skills = new HashSet<>();
		skills.add(new Skill("Java", "Programming"));
		skills.add(new Skill("Oracle", "Database"));
		Member member = new Member(new MemberId(mId), "Test Member", skills);
		assertEquals(member.areSkillsValid(requirements), Boolean.TRUE);
	}
	
	@Test
	void testSkillValidationShouldBeFalse() {
		List<String> requirements = new ArrayList<>();
		Collections.addAll(requirements, "Java", "Oracle", "Scrum");
		Set<Skill> skills = new HashSet<>();
		skills.add(new Skill("Chinese", "Language"));
		Member member = new Member(new MemberId(mId), "Test Member", skills);
		assertEquals(member.areSkillsValid(requirements), Boolean.FALSE);
	}
}
