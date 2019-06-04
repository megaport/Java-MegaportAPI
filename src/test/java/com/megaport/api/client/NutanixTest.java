package com.megaport.api.client;

import static org.junit.Assert.*;
import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NutanixTest extends SessionHelper {

	MegaportApiSession session;

	@Before
	public void init() throws Exception{

		session = getSession();
		System.out.println("## Environment: " + Environment.STAGING);
		assertTrue(session.isValid());

	}

	@Test
	public void testOrderVxcToNutanix() throws Exception{
		NutanixPortsDto nutanixPorts = session.findNutanixPorts(UUID.randomUUID().toString());
		assertNotNull(nutanixPorts);
		System.out.println(">> Nutanix Ports: " + nutanixPorts);

		MegaportServiceDto goodVxc = createGoodVxc(nutanixPorts);

		String order = session.placeOrder(Collections.singletonList(goodVxc));
		assertNotNull(order);

		System.out.println(">> VXC to Nutanix Order: " + order);
	}


	/**
	 * Create a VXC connection from an existing Megaport (aEnd = sydneyPortUid = f7f318e2-1cce-4a94-922f-3d8bfc1c8290) to Nutanix (bEnd)
	 * @param nutanixPortsDto
	 * @return
	 */
	private MegaportServiceDto createGoodVxc(NutanixPortsDto nutanixPortsDto){

		MegaportServiceDto dto = new MegaportServiceDto();

		String sydneyPortUid = "f7f318e2-1cce-4a94-922f-3d8bfc1c8290";
		dto.setProductUid(sydneyPortUid);

		VxcServiceDto vxcDto = new VxcServiceDto();
		vxcDto.setProductType(ProductType.VXC);
		vxcDto.setProductName("My VXC to Nutanix");
		vxcDto.setRateLimit(nutanixPortsDto.getBandwidth());
		vxcDto.setProvisioningStatus(ProvisioningStatus.DESIGN);

		VxcEndDto endA = new VxcEndDto();
		endA.setVlan(0);

		VxcEndDto endB = new VxcEndDto();
		endB.setVlan(0);
		endB.setProductUid(nutanixPortsDto.getMegaports().get(0).getProductUid());

		Map<String,Object> standardNutanixPartnerConfig = new HashMap<>();
		standardNutanixPartnerConfig.put("connectType", "NUTANIX");
		standardNutanixPartnerConfig.put("serviceKey", nutanixPortsDto.getServiceKey());
		standardNutanixPartnerConfig.put("bVlan", 0);
		standardNutanixPartnerConfig.put("maxRateLimit", nutanixPortsDto.getBandwidth());

		Map<String,Object> peer = new HashMap<>();
		peer.put("type", "private");
		standardNutanixPartnerConfig.put("peers", Collections.singletonList(peer));

		endB.setPartnerConfig(standardNutanixPartnerConfig);

		vxcDto.setaEnd(endA);
		vxcDto.setbEnd(endB);

		dto.getAssociatedVxcs().add(vxcDto);

		return dto;
	}

}
