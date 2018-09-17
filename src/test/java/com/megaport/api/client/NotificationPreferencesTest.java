package com.megaport.api.client;

import static org.junit.Assert.assertTrue;

import com.megaport.api.dto.Environment;
import com.megaport.api.dto.notifications.NotificationPreferencesDto;
import com.megaport.api.dto.notifications.MessageDestination;
import com.megaport.api.dto.notifications.MessageGroupPreferencesDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NotificationPreferencesTest {

	MegaportApiSession session;

	@Before
	public void init() throws Exception{

		session = new MegaportApiSession(Environment.LOCALHOST, "api.test", "Abc123", null);
		assertTrue(session.isValid());

	}

	@Test
	public void getNotificationPreferences() throws Exception {
		NotificationPreferencesDto prefs = session.getNotificationPreferences();
		Assert.assertNotNull(prefs);
		Assert.assertTrue(prefs.getDestinations().size() > 0);
	}

	@Test
	public void setNotificationPreferences() throws Exception {
		NotificationPreferencesDto prefs = session.getNotificationPreferences();
		Assert.assertNotNull(prefs);
		Assert.assertTrue(prefs.getDestinations().size() > 0);
		for (MessageDestination destination : prefs.getDestinations().keySet()) {
			for (MessageGroupPreferencesDto messageGroupPrefs : prefs.getDestinations().get(destination)) {
				messageGroupPrefs.setSubscribed(true);
			}
		}

		NotificationPreferencesDto updatedPrefs = session.putNotificationPreferences(prefs);
		for (MessageDestination destination : updatedPrefs.getDestinations().keySet()) {
			for (MessageGroupPreferencesDto messageGroupPrefs : prefs.getDestinations().get(destination)) {
				Assert.assertTrue(messageGroupPrefs.isSubscribed());
			}
		}

	}
}
