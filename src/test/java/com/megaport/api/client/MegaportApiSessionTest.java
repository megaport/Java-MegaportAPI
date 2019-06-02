package com.megaport.api.client;

import com.megaport.api.dto.CompanyDto;
import com.megaport.api.dto.Environment;
import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.TechnicalServiceDto;
import com.megaport.api.exceptions.InvalidCredentialsException;
import com.megaport.api.exceptions.ServiceUnavailableException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class MegaportApiSessionTest extends SessionHelper {

    @Test
    public void testCreateSession() throws Exception{
        String goodToken;
        MegaportApiSession session = getSession();
        assertTrue(session.isValid());

        session = getSession();
        assertTrue(session.isValid());

        goodToken = session.getToken();

        session = getSession(goodToken);
        assertTrue(session.isValid());

        session = getSession(goodToken);
        assertTrue(session.isValid());

    }

    @Test
    public void testFailValidation() throws Exception{

        try {
            MegaportApiSession session = getSession("api.test", "bad-password");
            fail();
        } catch (InvalidCredentialsException e){
            // expect to be here bad password
        }

        try {
            MegaportApiSession session = getSession("bad-token");
            fail();
        } catch (InvalidCredentialsException e){
            // expect to be here bad password
        }

    }

    @Test
    public void testServiceUnavailable() throws Exception{

        try {
            MegaportApiSession session = getSession();
            session.simulateServiceUnavailable();
            fail();
        } catch (ServiceUnavailableException e){
            // expect to be here bad password
        }

    }

    @Test
    public void testBadHost(){

        MegaportApiSession session = null;
        try {
            session = new MegaportApiSession("htps://api-staging.megaport.com", "api.test", "Abc123");
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass().getName(), "java.net.UnknownHostException");
            assertEquals(e.getMessage(), "You must include either http:// or https:// prefix, and optionally :<port> suffix in the server name.");
        }

        try {
            session = new MegaportApiSession("https://api-raining.megaport.com", "api.test", "Abc123");
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass().getName(), "java.net.UnknownHostException");
        }

    }

    @Ignore
    @Test
    public void testPartnerCustomers() throws Exception{

        MegaportApiSession session = getSession("");
        List<CompanyDto> partnerCustomers = session.findPartnerCustomers();
        assertTrue(partnerCustomers.size() > 0);

        List<MegaportServiceDto> partnerCustomersProducts = session.findPartnerCustomersProducts();
        assertTrue(partnerCustomersProducts.size() > 0);

    }

}