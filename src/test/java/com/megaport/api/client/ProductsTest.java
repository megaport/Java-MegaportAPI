package com.megaport.api.client;

import com.megaport.api.dto.Environment;
import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.PartnerPortDto;
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
    public void testFindPartnerPorts() throws Exception{

        List<PartnerPortDto> ports = session.findPartnerPorts();

        // since this is hitting the training system, there will be a variable number of services, but never 0
        assertTrue(ports.size() > 0);

    }

}