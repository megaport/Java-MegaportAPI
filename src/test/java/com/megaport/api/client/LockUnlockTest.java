package com.megaport.api.client;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.megaport.api.dto.Environment;
import com.megaport.api.dto.LockStatus;
import com.megaport.api.dto.MegaportServiceDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LockUnlockTest {

	MegaportApiSession session;

	@Before
	public void init() throws Exception {

		session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123");
		assertTrue(session.isValid());

	}

	@Test
	public void testLockUnlockPort() {
		try {
			List<MegaportServiceDto> ports = session.findPorts();
			Assert.assertTrue(ports.size() > 0);
			final String productUid = ports.get(0).getProductUid();
			session.lockOrUnlock(productUid, LockStatus.LOCKED);
			ports = session.findPorts().stream()
					.filter(it -> it.getProductUid().equals(productUid))
					.collect(Collectors.toList());
			Assert.assertTrue(ports.get(0).isLocked());

			session.lockOrUnlock(productUid, LockStatus.UNLOCKED);
			ports = session.findPorts().stream()
					.filter(it -> it.getProductUid().equals(productUid))
					.collect(Collectors.toList());
			Assert.assertFalse(ports.get(0).isLocked());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
