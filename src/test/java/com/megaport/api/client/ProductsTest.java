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

    MegaportServiceDto configuredPort = null;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.LOCALHOST, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned

        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                configuredPort = port;
                break;
            }
        }

        if (configuredPort == null) throw new RuntimeException("Not much point going on, since there are no configured ports to test...");


    }

    @Test
    public void testFindPorts() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

    @Test
    public void testFindServiceDetail() throws Exception{

        Object product = session.findServiceDetail(configuredPort.getProductUid());
        assertTrue(product != null);

    }

    @Test
    public void testFindServiceLogs() throws Exception{

        List<ActiveLogDto> serviceLogs = session.findServiceLogs(configuredPort.getProductUid());
        assertTrue(serviceLogs != null);

    }

    @Test
    public void testFindUsageData() throws Exception{

        GraphDto graphDto = session.findServiceUsage(configuredPort.getProductUid(), null, null);
        assertTrue(graphDto != null);

        Date to = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date from = cal.getTime();
        graphDto = session.findServiceUsage(configuredPort.getProductUid(), from, to);
        assertTrue(graphDto != null);

    }

    @Test
    public void testFindPartnerPorts() throws Exception{

        List<PartnerPortDto> ports = session.findPartnerPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

}