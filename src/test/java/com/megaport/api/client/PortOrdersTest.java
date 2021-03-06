package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import com.megaport.api.util.JsonConverter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class PortOrdersTest extends SessionHelper {

    MegaportApiSession session;

    @Before
    public void init() throws Exception {

        session = getSession();
        assertTrue(session.isValid());

    }

    @Test
    @Ignore
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

    @Test
    @Ignore
    public void testVRouterToL2Port() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createVRouterToPort());

        String orderResponse = session.placeOrder(order);

        System.out.println(orderResponse);

    }



    private MegaportServiceDto createVRouterToPort() {

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setProductUid("97c3ffee-0668-4362-b540-cda64d27dad9"); // existing VRouter, owned by test account, this is the A End


        // the child VXC connects the vRouter with another vRouter
        VxcServiceDto vxcDto = new VxcServiceDto();
        vxcDto.setProductName("VXC between VRouter and layer2 ports");
        vxcDto.setRateLimit(150);

        VxcEndDto endA = new VxcEndDto();
        endA.setPartnerConfig(getVRouterConfig(10, "192.0.2.1/30", 42234, "192.0.2.1", "192.0.2.2", "192.0.2.1"));

        VxcEndDto endB = new VxcEndDto();
        endB.setProductUid("6afff5db-1f00-4110-9eae-4866ef85bdd5"); //  this is an existing L2 port

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
    @Ignore
    public void testOrderVRouterUpgrade() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createVRouterUpgrade());

        String orderResponse = session.placeOrder(order);
        HashMap<String, Object> result = JsonConverter.fromJson(orderResponse);

        // expecting two services here
        int firstIndex = orderResponse.indexOf("technicalServiceUid=") + "technicalServiceUid=".length();
        int secondIndex = orderResponse.indexOf("vxcJTechnicalServiceUid=", firstIndex +1) + "vxcJTechnicalServiceUid=".length();
        String firstService = orderResponse.substring(firstIndex, firstIndex + 36);
        String secondService = orderResponse.substring(secondIndex, secondIndex + 36);

        System.out.println(orderResponse);

        session.lifecycle(firstService, LifecycleAction.CANCEL_NOW, null);

        List<MegaportServiceDto> ports = session.findPorts();
        Boolean foundIt = false;
        for (MegaportServiceDto dto : ports){
            if (dto.getProductUid().equals(firstService)){
                assertEquals(ProvisioningStatus.DECOMMISSIONED, dto.getProvisioningStatus());
                for (VxcServiceDto vxc : dto.getAssociatedVxcs()){
                    assertTrue(vxc.getProductUid().equals(secondService));
                    assertEquals(ProvisioningStatus.DECOMMISSIONED, vxc.getProvisioningStatus());
                }
                foundIt = true;
            }
        }
        assertEquals(true, foundIt);

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
        order.add(createGoodPort(false, 3, 10000));

        // prices for this account will be $0
        List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
        assertEquals(1, serviceLineItemDtos.size());

    }

    @Test
    public void testValidateLag() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodLag("new-lag-7-ports",false, 164, 10000, 7));

        List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
        assertEquals(1, serviceLineItemDtos.size());
        System.out.println(serviceLineItemDtos.get(0));

    }

    @Test
    public void testValidateMcr() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodMcr(140, 1000));

        List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
        assertEquals(1, serviceLineItemDtos.size());
        assertEquals(ProductType.MEGAPORT.getCode(), serviceLineItemDtos.get(0).getProductType());
        assertEquals(ProductType.MEGAPORT, serviceLineItemDtos.get(0).getPrice().getProductType());

    }

    @Test
    public void testPortPrice() throws Exception {

        PriceDto megaportPrice = session.findMegaportPrice(163, 10000, 1, false);

        System.out.println(megaportPrice.toString());

        assertEquals(new BigDecimal(500), megaportPrice.getMonthlyRate());

    }

    @Test
    public void testVlanValidation() throws Exception {

        List<Integer> vlans = session.validateVlan("d185d0b7-3e99-4071-8e00-5e096396b1f5", 3334);

        System.out.println(vlans.toString());

        assertTrue(vlans.size() > 0);

    }

    @Test
    public void testMCRPrice() throws Exception {

        PriceDto mcrPrice = session.findMegaportPrice(140, 5000, 1, true);

        System.out.println(mcrPrice.toString());

        assertEquals(new BigDecimal(1000), mcrPrice.getMonthlyRate());

    }

    @Test
    public void testOrderPort() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodPort(false, 3, 10000));

        // prices for this account will be $0
        String orderResponse = session.placeOrder(order);

        System.out.println(orderResponse);
    }

    @Test
    public void testOrderLags() throws Exception {
        Integer locationId = 156;
        String lagName = "new-lag-2-ports";

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodLag(lagName, false, locationId, 10000, 2));
        // new lag order of 2 ports
        String lagOrderResponse = session.placeOrder(order);
        System.out.println("new lag order result: " + lagOrderResponse);
        Integer aggregationId = null;
        List<MegaportServiceDto> ports = session.findPorts();
        for (int i=0; i < ports.size(); i++) {
            MegaportServiceDto port = ports.get(i);
            if (port.getProductName().equals(lagName) && port.getLagPrimary()) {
                aggregationId = port.getAggregationId();
            }
        }
        System.out.println("aggregationId: " + aggregationId);
        assertNotNull(aggregationId);

        // new validate order for adding 3 x new ports to an existing lag (aggregationId)
        order.clear();
        order.add(createGoodPortsInLag(false, locationId, 10000, aggregationId, 3));
        List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
        for(ServiceLineItemDto dto: serviceLineItemDtos) {
            System.out.println("new port validation result: " + dto);
        }

        // new order to add 3 x new ports to an existing lag (aggregationId)
        order.clear();
        order.add(createGoodPortsInLag(false, locationId, 10000, aggregationId, 3));
        String newOrderJson = session.placeOrder(order);
        System.out.println("new ports order: " + newOrderJson);
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

    @Test
    public void testIssue() throws Exception {
        MegaportServiceDto ms = new MegaportServiceDto();
        ms.setLocationId(66);  // determined above
        ms.setProductType(ProductType.MEGAPORT);
        ms.setTerm(1);
        ms.setProductName("My Test Port");
        ms.setPortSpeed(1000);
        ms.setProvisioningStatus(ProvisioningStatus.DESIGN);
        List<MegaportServiceDto> msl = new ArrayList<>();
        msl.add(ms);
        List<ServiceLineItemDto> sli = session.validateOrder(msl);
    }

    private MegaportServiceDto createGoodPort(Boolean virtual, Integer locationId, Integer speed) {


        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(locationId);
        dto.setTerm(24);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(speed);
        dto.setVirtual(virtual);

        return dto;
    }

    private MegaportServiceDto createGoodLag(String lagName, Boolean virtual, Integer locationId, Integer speed, Integer lagPortCount) {
        MegaportServiceDto dto = createGoodPort(virtual, locationId, speed);
        dto.setProductName(lagName);
        dto.setLagPortCount(lagPortCount);
        return dto;
    }

    private MegaportServiceDto createGoodPortInLag(Boolean virtual, Integer locationId, Integer speed, Integer lagServiceId) {
        MegaportServiceDto dto = createGoodPort(virtual, locationId, speed);
        dto.setLagPortCount(1);
        dto.setAggregationId(lagServiceId);
        return dto;
    }

    private MegaportServiceDto createGoodPortsInLag(Boolean virtual, Integer locationId, Integer speed, Integer lagServiceId, Integer newPortCount) {
        MegaportServiceDto dto = createGoodPortInLag(virtual, locationId, speed, lagServiceId);
        dto.setLagPortCount(newPortCount);
        return dto;
    }

    private MegaportServiceDto createGoodMcr(Integer locationId, Integer speed) {


        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setLocationId(locationId);
        dto.setTerm(1);
        dto.setProductType(ProductType.MEGAPORT);
        dto.setProductName("My New Port");
        dto.setProvisioningStatus(ProvisioningStatus.DESIGN);
        dto.setPortSpeed(speed);
        dto.setVirtual(true);

        ConfigDto config = new ConfigDto();
        config.setMcrAsn(1234567L);
        dto.setConfig(config);

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