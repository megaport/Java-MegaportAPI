package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.InvalidCredentialsException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class ProductsTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

    }

    @Test
    public void testFindPorts() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

    @Test
    public void testFindServiceDetail() throws Exception{

        TechnicalServiceDto product = session.findServiceDetail("533d8ab9-2026-4617-bd19-cb9976417ba5");
        assertTrue(product != null);

    }

    @Test
    public void testFindServiceLogs() throws Exception{

        List<ActiveLogDto> serviceLogs = session.findServiceLogs("533d8ab9-2026-4617-bd19-cb9976417ba5");
        assertTrue(serviceLogs != null);

    }

    @Test
    public void testFindPartnerPorts() throws Exception{

        List<PartnerPortDto> ports = session.findPartnerPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

}