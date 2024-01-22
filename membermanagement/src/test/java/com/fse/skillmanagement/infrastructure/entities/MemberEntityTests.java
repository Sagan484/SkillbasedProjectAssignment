package com.fse.skillmanagement.infrastructure.entities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fse.skillmanagement.domain.aggregates.member.Member;
import com.fse.skillmanagement.domain.aggregates.member.MemberId;

@SpringBootTest
class MemberEntityTests {
	
	private Integer mId = 1000;
	
	@Test
	void testShouldThrowNPEIfSkillsAreNull() {
		assertThrows(NullPointerException.class,
				()-> {
					new MemberEntity(new Member(new MemberId(mId), "Test Member", null));
				});
	}

}
