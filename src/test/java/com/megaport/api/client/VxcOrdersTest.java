package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class VxcOrdersTest extends SessionHelper {

    MegaportApiSession session;

    private final String sydneyPortUid = "f7f318e2-1cce-4a94-922f-3d8bfc1c8290";
    private final String brisbanePortUid = "d185d0b7-3e99-4071-8e00-5e096396b1f5";


    @Before
    public void init() throws Exception {

        session = getSession();
        assertTrue(session.isValid());

    }

    //https://api.megaport.com/v2//pricebook/vxc?aLocationId=47&speed=100&bLocationId=37&connectType=AWS
    @Test
    public void testVxcPrice() throws Exception {

        PriceDto vxcPrice = session.findVxcPrice(47,37, 100, "DEFAULT");

        System.out.println(vxcPrice.toString());

        assertEquals(new BigDecimal("1485"), vxcPrice.getMonthlyRate());

    }

    @Test
    public void testVxcPrice2() throws Exception {

        PriceDto vxcPrice = session.findVxcPrice(140,140, 10000, "DEFAULT");

        System.out.println(vxcPrice.toString());

        assertEquals(new BigDecimal("200"), vxcPrice.getMonthlyRate());

    }

    @Test
    @Ignore
    public void testVxcPrice3() throws Exception {

        MegaportApiSession testSession = new MegaportApiSession(Environment.LOCALHOST, "5555e6cd-ae58-ac20-90b4-1de713c8a62d");

        PriceDto vxcPrice = testSession.findVxcPrice(140,140, 10000, "DEFAULT");

        System.out.println(vxcPrice.toString());

        assertEquals(new BigDecimal("200"), vxcPrice.getMonthlyRate());

    }


    @Test
    public void testValidateVxcOrder() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodVxc());

        // prices for this account will be $0
        List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
        assertEquals(1, serviceLineItemDtos.size());

    }

    @Test
    public void testOrderVxc() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createGoodVxc());

        // prices for this account will be $0
        String orderResponse = session.placeOrder(order);

    }


    @Test
    public void testCreateBadVxcNoSpeed() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcNoSpeed());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcBadSpeed() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcBadSpeed());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcBadSpeed2() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcBadSpeed2());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcNoBEnd() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcNoBEnd());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcNoBEndProductUid() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcNoBEndProductUid());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcBadBEndProductUid() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcBadBEndProductUid());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcNoEnds() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcNoEnds());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadVxcNoVLANs() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcNoVLANs());

        try {
            List<ServiceLineItemDto> serviceLineItemDtos = session.validateOrder(order);
            assertEquals(1, serviceLineItemDtos.size());
        } catch (BadRequestException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testCreateBadVxcBadVLANs() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVxcBadVLANs());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    private MegaportServiceDto createGoodVxc(){
        
        MegaportServiceDto dto = new MegaportServiceDto();
        dto.setPortSpeed(10000);

        dto.setProductUid(brisbanePortUid);

        VxcServiceDto vxcDto = new VxcServiceDto();
        vxcDto.setProductName("My VXC");
        vxcDto.setRateLimit(100);

        VxcEndDto endA = new VxcEndDto();
        endA.setVlan(222);

        VxcEndDto endB = new VxcEndDto();
        endB.setVlan(222);
        endB.setProductUid(sydneyPortUid);

        vxcDto.setaEnd(endA);
        vxcDto.setbEnd(endB);

        dto.getAssociatedVxcs().add(vxcDto);

        return dto;
    }

    private MegaportServiceDto createBadVxcNoSpeed(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).setRateLimit(null);
        return dto;
    }

    private MegaportServiceDto createBadVxcBadSpeed(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).setRateLimit(-12);
        return dto;
    }

    private MegaportServiceDto createBadVxcBadSpeed2(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).setRateLimit(1000000);
        return dto;
    }

    private MegaportServiceDto createBadVxcNoBEnd(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).setbEnd(null);
        return dto;
    }

    private MegaportServiceDto createBadVxcNoBEndProductUid(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).getbEnd().setProductUid(null);
        return dto;
    }

    private MegaportServiceDto createBadVxcBadBEndProductUid(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).getbEnd().setProductUid("aasdadsadadadsasd");
        return dto;
    }

    private MegaportServiceDto createBadVxcNoEnds(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).setaEnd(null);
        dto.getAssociatedVxcs().get(0).setbEnd(null);
        return dto;
    }

    private MegaportServiceDto createBadVxcNoVLANs(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).getaEnd().setVlan(null);
        dto.getAssociatedVxcs().get(0).getbEnd().setVlan(null);
        return dto;
    }

    private MegaportServiceDto createBadVxcBadVLANs(){
        MegaportServiceDto dto = createGoodVxc();
        dto.getAssociatedVxcs().get(0).getaEnd().setVlan(-12);
        dto.getAssociatedVxcs().get(0).getbEnd().setVlan(199999999);
        return dto;
    }

}