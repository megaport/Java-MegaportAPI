package com.megaport.api.client;

import com.megaport.api.dto.AzurePortsDto;
import com.megaport.api.dto.Environment;
import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.PartnerPortDto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class AzureTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.STAGING, "api.test", "s0me-s3cret#", null);
        assertTrue(session.isValid());

    }

    @Test
    public void testFindExpressRoutePorts() throws Exception{

        AzurePortsDto ports = session.findAzurePorts("19da14bf-6ba5-40bc-bf09-a974f4a06f6b");
        assertTrue(ports != null);

    }

}