package com.megaport.api.dto.notifications;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

public class MessageGroupPreferencesDto {

	private boolean subscribed;
	private MessageGroup messageGroup;
	private List<String> details;

	public MessageGroupPreferencesDto() {
	}

	public MessageGroupPreferencesDto(boolean subscribed, MessageGroup messageGroup) {
		this.subscribed = subscribed;
		this.messageGroup = messageGroup;
	}

	@JsonGetter
	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	@JsonGetter
	public MessageGroup getMessageGroup() {
		return messageGroup;
	}

	@JsonGetter
	public List<String> getDetails() {
		return details;
	}
}
