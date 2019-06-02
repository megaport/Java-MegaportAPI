package com.megaport.api.client;

import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class PriceCheckTest extends SessionHelper {

    MegaportApiSession session;

    @Before
    public void init() throws Exception {

        session = getSession("fake");
        assertTrue(session.isValid());

    }

    @Test
    @Ignore
    public void testVxcPrice() throws Exception {

        PriceCheckDto vxcPrice = session.speedChangePriceCheck("fake", 2018, 5, 100);

        System.out.println(vxcPrice.toString());



    }

}