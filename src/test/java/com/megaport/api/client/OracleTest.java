package com.megaport.api.client;

import com.megaport.api.dto.AzurePortsDto;
import com.megaport.api.dto.CspPortsDto;
import com.megaport.api.dto.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class OracleTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.STAGING, "api.test", "s0me-s3cret#", null);
        assertTrue(session.isValid());

    }

    @Test
    public void testFindOraclePorts() throws Exception{

        CspPortsDto ports = session.findOraclePorts("ocid1.virtualcircuit.oc1.phx.aaaaaaaajq5twtkob6slb3qxbmdfidnvnbiw2kgmqld3ifj4cpgr5uwe45ka");
        assertTrue(ports != null);

    }

}