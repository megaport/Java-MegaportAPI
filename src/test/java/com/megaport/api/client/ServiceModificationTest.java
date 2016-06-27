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
public class ServiceModificationTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

    }

    @Test
    public void testChangeServiceName() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        MegaportServiceDto configuredPort = null;
        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                configuredPort = port;
                break;
            }
        }

        if (configuredPort != null) {
            String productUid = configuredPort.getProductUid();
            session.modifyPort("New Name", productUid);
            TechnicalServiceDto product = session.findServiceDetail(productUid);
            assertEquals("New Name", product.getServiceName());
        }
    }

}