package com.megaport.api.client;

import com.megaport.api.dto.AzurePortsDto;
import com.megaport.api.dto.Environment;
import com.megaport.api.dto.PortLocationDto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class LocationsTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.QA, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

    }

    @Test
    public void testLocations() throws Exception{

        List<PortLocationDto> ports = session.findPortLocations();
        assertTrue(ports.size() > 0);

    }

}