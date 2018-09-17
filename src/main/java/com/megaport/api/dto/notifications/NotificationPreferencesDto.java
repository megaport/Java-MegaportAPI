package com.megaport.api.dto.notifications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

public class NotificationPreferencesDto {

	private EnumMap<MessageDestination, List<MessageGroupPreferencesDto>> destinations;

	public NotificationPreferencesDto() {
		destinations = new EnumMap<>(MessageDestination.class);
	}

	public NotificationPreferencesDto(MessageDestination messageDestination,
	                                  List<MessageGroupPreferencesDto> preferences) {
		this();
		addPreferences(preferences, messageDestination);
	}

	public void addPreferences(List<MessageGroupPreferencesDto> preferences, MessageDestination forDestination) {
		List<MessageGroupPreferencesDto> existing = destinations.get(forDestination);
		if (existing == null) {
			existing = new ArrayList<>();
		}
		existing.addAll(preferences);
	}

	public void addPreference(MessageGroupPreferencesDto preference, MessageDestination forDestination) {
		addPreferences(Collections.singletonList(preference), forDestination);
	}


	@JsonGetter
	public EnumMap<MessageDestination, List<MessageGroupPreferencesDto>> getDestinations() {
		return destinations;
	}

}
