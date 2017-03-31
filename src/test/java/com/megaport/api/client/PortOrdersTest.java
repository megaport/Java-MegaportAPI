package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import com.megaport.api.util.JsonConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class PortOrdersTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception {

        
//        session = new MegaportApiSession(Environment.STAGING, "api.test", "s0me-s3cret#");
//        session = new MegaportApiSession(Environment.STAGING, "");
        session = new MegaportApiSession("https://api-kenobi.megaport.com", "");
//        session = new MegaportApiSession(Environment.LOCALHOST, "");
        assertTrue(session.isValid());

    }

    @Test
    public void testVRouterToVRouter() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createVRouterToVRouter());

        String orderResponse = session.placeOrder(order);

        System.out.println(orderResponse);

    }

    private MegaportServiceDto createVRouterToVRouter() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setProductUid("981d67a4-e72e-4ce9-80af-d2608b11bff9"); // existing VRouter, owned by test account, this is the A End


        // the child VXC connects the vRouter with another vRouter
        VxcServiceDto vxcDto = new VxcServiceDto();
        vxcDto.setProductName("VXC between two VRouter ports");
        vxcDto.setRateLimit(150);

        VxcEndDto endA = new VxcEndDto();
        endA.setPartnerConfig(getVRouterConfig(10, "192.0.2.1/30", 42234, "192.0.2.1", "192.0.2.2", "192.0.2.1"));

        VxcEndDto endB = new VxcEndDto();
        endB.setProductUid("e5de5e25-05b9-4e7d-9c9a-b99fdad85ae6");
        endB.setPartnerConfig(getVRouterConfig(13, "192.0.2.2/30", 42234, "192.0.2.2", "192.0.2.1", "192.0.2.2"));

        // now wrap it up...
        vxcDto.setaEnd(endA);
        vxcDto.setbEnd(endB);
        dto.getAssociatedVxcs().add(vxcDto);

        return dto;
    }

    private Map<String,Object> getVRouterConfig(Integer vlan, String subnet, Integer asn, String localIp, String peerIp, String natIp){

        String json = "\"connectType\": \"VROUTER\",\n" +
                "            \"interfaces\": [\n" +
                "              {\n" +
                "                \"vlan\": " + vlan + ",\n" +
                "                \"ipAddresses\": [\n" +
                "                  \"" + subnet + "\"\n" +
                "                ],\n" +
                "                \"bgpConnections\": [\n" +
                "                  {\n" +
                "                    \"peerAsn\": " + asn + ",\n" +
                "                    \"localIpAddress\": \"" + localIp + "\",\n" +
                "                    \"peerIpAddress\": \"" + peerIp + "\",\n" +
                "                    \"password\": \"bgpPassword\"\n" +
                "                  }\n" +
                "                ],\n" +
                "                \"natIpAddresses\": [\n" +
                "                  \"" + natIp + "\"\n" +
                "                ]\n" +
                "              }\n" +
                "            ]";

        return JsonConverter.fromJson(json);

    }


    @Test
    public void testOrderVRouterUpgrade() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createVRouterUpgrade());

        String orderResponse = session.placeOrder(order);

        System.out.println(orderResponse);

    }

    private MegaportServiceDto createVRouterUpgrade() {

        MegaportServiceDto dto = new MegaportServiceDto();

        // the parent is the new vRouter
        dto.setLocationId(56); // location that has vRouter available
        dto.setTerm(12);
        dto.setVirtual(true);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("VRouter Port loc 56");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(5000);

        // the child VXC connects the new vRouter with the existing layer 2 port
        VxcServiceDto vxcDto = new VxcServiceDto();
        vxcDto.setProductName("VXC between VRouter port and layer 2 port");
        vxcDto.setRateLimit(1000);

        VxcEndDto endA = new VxcEndDto();

        VxcEndDto endB = new VxcEndDto();
        endB.setProductUid("9395a2f7-93d1-4492-90ba-e914b335690e");

        vxcDto.setaEnd(endA);
        vxcDto.setbEnd(endB);

        dto.getAssociatedVxcs().add(vxcDto);

        return dto;
    }





    @Test
    public void testValidatePort() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodPort());

        // prices for this account will be $0
        List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
        assertEquals(1, serviceLineItemDtos.size());

    }

    @Test
    public void testOrderPort() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodPort());

        // prices for this account will be $0
        String orderResponse = session.placeOrder(order);

        System.out.println(orderResponse);

    }

    @Test
    public void testValidatePortOrderMissingSpeed() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadPortMissingSpeed());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testValidatePortOrderBadSpeed() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadPortInvalidSpeed());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testValidatePortNoName() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadPortNoName());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testValidatePortBadLocation() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadPortBadLocation());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testValidatePortMissingStatus() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadPortMissingStatus());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    private MegaportServiceDto createGoodPort() {


        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(3);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(10000);

        return dto;
    }

    private MegaportServiceDto createBadPortNoName() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(3);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(10000);

        return dto;
    }

    private MegaportServiceDto createBadPortMissingSpeed() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(3);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);

        return dto;
    }

    private MegaportServiceDto createBadPortBadLocation() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(-3);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(10000);

        return dto;
    }

    private MegaportServiceDto createBadPortInvalidSpeed() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(-3);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(3);

        return dto;
    }

    private MegaportServiceDto createBadPortMissingStatus() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(-3);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setPortSpeed(10000);

        return dto;
    }

}