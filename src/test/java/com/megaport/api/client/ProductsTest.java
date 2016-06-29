package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.InvalidCredentialsException;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
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

        session = new MegaportApiSession(Environment.LOCALHOST, "api.test", "s0me-s3cret#");
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
    public void testFindUsageData() throws Exception{

        GraphDto graphDto = session.findServiceUsage("533d8ab9-2026-4617-bd19-cb9976417ba5", null, null);
        assertTrue(graphDto != null);

        Date to = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date from = cal.getTime();
        graphDto = session.findServiceUsage("533d8ab9-2026-4617-bd19-cb9976417ba5", from, to);
        assertTrue(graphDto != null);

    }

    @Test
    public void testFindPartnerPorts() throws Exception{

        List<PartnerPortDto> ports = session.findPartnerPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

}