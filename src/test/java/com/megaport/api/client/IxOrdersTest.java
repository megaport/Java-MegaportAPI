package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class IxOrdersTest {

    MegaportApiSession session;

    private final String brisbanePortUid = "d185d0b7-3e99-4071-8e00-5e096396b1f5";


    @Before
    public void init() throws Exception {

        session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123");
        assertTrue(session.isValid());

    }

    @Test
    public void testFindAvailableIxByLocation() throws Exception{

        List<IxDto> ixForLocation = session.findIxForLocation(3);
        assertEquals(18, ixForLocation.size());

    }

    //https://api.megaport.com/v2//pricebook/ix?ixType=Ashburn%20IX&portLocationId=90&speed=100
    @Test
    public void testIxPrice() throws Exception {

        PriceDto vxcPrice = session.findIxPrice(90,"Ashburn IX", 100);

        System.out.println(vxcPrice.toString());

        assertEquals(new BigDecimal("160"), vxcPrice.getMonthlyRate());

    }

    @Test
    public void testCreateBadIxAsn() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadAsn());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxVlan() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadVlan());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxBadSpeed() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadBadSpeed());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxBadSpeed2() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadBadSpeed2());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxBadAsn2() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadAsn2());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxEmpty() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadIxEmpty());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxExisting() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadIxExistingIx());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateBadIxNoIx() throws Exception {

        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(createBadIxNoIx());

        try {
            session.validateOrder(order);
            fail();
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

    }

    private MegaportServiceDto createGoodIx(){

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setProductUid(brisbanePortUid);
        dto.setPortSpeed(1000);

        IxServiceDto ixDto = new IxServiceDto();
        ixDto.setProductName("My Melbourne IX");
        ixDto.setNetworkServiceType("Melbourne IX");
        ixDto.setAsn(12345L);
        ixDto.setMacAddress("00:23:45:67:89:ab");
        ixDto.setRateLimit(500);
        ixDto.setVlan(101);

        dto.getAssociatedIxs().add(ixDto);

        return dto;
    }

    private MegaportServiceDto createGoodIxSydneyLocation(){

        MegaportServiceDto dto = new MegaportServiceDto();

        dto.setProductUid(brisbanePortUid);
        dto.setPortSpeed(1000);

        IxServiceDto ixDto = new IxServiceDto();
        ixDto.setProductName("My Sydney IX");
        ixDto.setNetworkServiceType("Sydney IX");
        ixDto.setAsn(12345L);
        ixDto.setMacAddress("00:23:45:67:89:ab");
        ixDto.setRateLimit(500);
        ixDto.setVlan(102);

        dto.getAssociatedIxs().add(ixDto);

        return dto;
    }

    private MegaportServiceDto createBadAsn(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setAsn(-1L);
        return dto;
    }

    private MegaportServiceDto createBadVlan(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setVlan(-13);
        return dto;
    }

    private MegaportServiceDto createBadBadSpeed(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setRateLimit(-3);
        return dto;
    }

    private MegaportServiceDto createBadBadSpeed2(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setRateLimit(999999999);
        return dto;
    }

    private MegaportServiceDto createBadAsn2(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setAsn(Long.MAX_VALUE);
        return dto;
    }

    private MegaportServiceDto createBadIxEmpty(){
        MegaportServiceDto dto = createGoodIx();
        dto.setProductUid(null);
        dto.getAssociatedIxs().clear();
        return dto;
    }

    private MegaportServiceDto createBadIxExistingIx(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setNetworkServiceType("Brisbane IX");
        return dto;
    }

    private MegaportServiceDto createBadIxNoIx(){
        MegaportServiceDto dto = createGoodIx();
        dto.getAssociatedIxs().get(0).setNetworkServiceType(null);
        return dto;
    }

}