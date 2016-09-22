package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

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