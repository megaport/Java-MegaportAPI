package com.megaport.api.client;

import static org.junit.Assert.*;

import com.megaport.api.dto.Environment;
import com.megaport.api.dto.NutanixPortsDto;
import org.junit.Before;
import org.junit.Test;

public class NutanixTest {

	MegaportApiSession session;

	@Before
	public void init() throws Exception{

		session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123", null);
		assertTrue(session.isValid());

	}

	@Test
	public void testFindNutanixPorts() throws Exception{
		NutanixPortsDto ports = session.findNutanixPorts("76025b4f-3a27-48da-a8bd-880a5e05c886");
		assertTrue(ports != null);
	}

}
