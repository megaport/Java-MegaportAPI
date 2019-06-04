package com.megaport.api.client;

import static org.junit.Assert.*;

import java.util.List;

import com.megaport.api.dto.Environment;
import com.megaport.api.dto.LockStatus;
import com.megaport.api.dto.MegaportServiceDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LockUnlockTest extends SessionHelper {

	MegaportApiSession session;

	@Before
	public void init() throws Exception {

		session = getSession();
		assertTrue(session.isValid());

	}

	@Test
	public void testLockUnlockPort() {
		try {
			List<MegaportServiceDto> ports = session.findPorts();
			Assert.assertTrue(ports.size() > 0);
			final String productUid = ports.get(0).getProductUid();
			System.out.println(productUid);
			session.lockOrUnlock(productUid, LockStatus.LOCKED);
			ports = session.findPorts();
			MegaportServiceDto port = findPort(ports, productUid);
			Assert.assertTrue(port.isLocked());

			session.lockOrUnlock(productUid, LockStatus.UNLOCKED);
			ports = session.findPorts();
			port = findPort(ports, productUid);
			Assert.assertFalse(port.isLocked());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	private MegaportServiceDto findPort(List<MegaportServiceDto> ports, String withId) {
		for (MegaportServiceDto port : ports) {
			if (port.getProductUid().equals(withId)) {
				return port;
			}
		}
		return null;
	}

}
