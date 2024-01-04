package com.fse.projectmanagement.domain.events;

public class MemberTemporalyAddedEvent extends DomainEvent {

	public MemberTemporalyAddedEvent(String payload) {
		super(payload);
	}
}
