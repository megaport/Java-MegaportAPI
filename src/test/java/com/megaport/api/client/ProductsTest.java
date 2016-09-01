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

    MegaportServiceDto livePort = null;

    MegaportServiceDto deployablePort = null;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned

        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                configuredPort = port;
                break;
            }
        }

        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.DEPLOYABLE)){
                deployablePort = port;
                break;
            }
        }

        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.LIVE)){
                livePort = port;
                break;
            }
        }

        //test to see we have populated ports before continuing, if we have any null ports, force a port to be populated
        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.DEPLOYABLE)
                    || port.getProvisioningStatus().equals(ProvisioningStatus.DEPLOYABLE)
                    || port.getProvisioningStatus().equals(ProvisioningStatus.LIVE)){
                if (configuredPort == null) {
                    configuredPort = port;
                    configuredPort.setProvisioningStatus(ProvisioningStatus.CONFIGURED);//force a configured port
                }
                if (deployablePort == null) {
                    deployablePort = port;
                    deployablePort.setProvisioningStatus(ProvisioningStatus.DEPLOYABLE);//force a configured port
                }
                if (livePort == null) {
                    livePort = port;
                    livePort.setProvisioningStatus(ProvisioningStatus.LIVE);//force a configured port
                }
                if (configuredPort != null && deployablePort != null && livePort !=null) {
                    break;
                }
            }
        }

        if (configuredPort == null ) throw new RuntimeException("Not much point going on, since there are no configured ports to test...");
        if (livePort == null) throw new RuntimeException("Not much point going on, since there are no live ports to test...");
        if (deployablePort == null) throw new RuntimeException("Not much point going on, since there are no deployable ports to test...");


    }

    @Test
    public void testFindPorts() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

    @Test
    public void testFindServiceDetail() throws Exception{

        Object product = session.findServiceDetail(livePort.getProductUid());
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