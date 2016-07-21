package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class ServiceLifecycleTest {

    MegaportApiSession session;
    MegaportServiceDto configuredPort = null;

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

    }

    @Test
    public void testCancel() throws Exception{

        if (configuredPort != null) {
            session.lifecycle(configuredPort.getProductUid(), LifecycleAction.CANCEL);

            MegaportServiceDto product = (MegaportServiceDto) session.findServiceDetail(configuredPort.getProductUid());
            assertEquals(ProvisioningStatus.CANCELLED, product.getProvisioningStatus());

            session.lifecycle(configuredPort.getProductUid(), LifecycleAction.UN_CANCEL);

            product = (MegaportServiceDto) session.findServiceDetail(configuredPort.getProductUid());
            assertEquals(ProvisioningStatus.CONFIGURED, product.getProvisioningStatus());
        }

    }


}