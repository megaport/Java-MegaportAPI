package com.megaport.api.client;

import com.megaport.api.dto.Environment;
import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.TechnicalServiceDto;
import com.megaport.api.exceptions.InvalidCredentialsException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class MegaportApiSessionTest {

    @Test
    public void testCreateSession() throws Exception{
        String goodToken;
        MegaportApiSession session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

        //session = new MegaportApiSession(Environment.STAGING, "api.test", "s0me-s3cret#");
        //assertTrue(session.isValid());

//        String goodToken = session.getToken();

//        session = new MegaportApiSession(Environment.STAGING, goodToken);
//        assertTrue(session.isValid());

        session = new MegaportApiSession("https://api-training.megaport.com", "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

        goodToken = session.getToken();

        session = new MegaportApiSession("https://api-training.megaport.com", goodToken);
        assertTrue(session.isValid());

        session = new MegaportApiSession("https://api-training.megaport.com:443", goodToken);
        assertTrue(session.isValid());

    }

    @Test
    public void testFailValidation() throws Exception{

//        try {
//            MegaportApiSession session = new MegaportApiSession(Environment.STAGING, "api.test", "bad-password");
//            fail();
//        } catch (InvalidCredentialsException e){
//            // expect to be here bad password
//        }

        try {
            MegaportApiSession session = new MegaportApiSession("https://api-training.megaport.com", "api.test", "bad-password");
            fail();
        } catch (InvalidCredentialsException e){
            // expect to be here bad password
        }

//        try {
//            MegaportApiSession session = new MegaportApiSession(Environment.STAGING, "bad-token");
//            fail();
//        } catch (InvalidCredentialsException e){
//            // expect to be here bad password
//        }

        try {
            MegaportApiSession session = new MegaportApiSession("https://api-training.megaport.com", "bad-token");
            fail();
        } catch (InvalidCredentialsException e){
            // expect to be here bad password
        }

    }

    @Test
    public void testBadHost(){

        MegaportApiSession session = null;
        try {
            session = new MegaportApiSession("htps://api-training.megaport.com", "api.test", "s0me-s3cret#");
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass().getName(), "java.net.UnknownHostException");
            assertEquals(e.getMessage(), "You must include either http:// or https:// prefix, and optionally :<port> suffix in the server name.");
        }

        try {
            session = new MegaportApiSession("https://api-raining.megaport.com", "api.test", "s0me-s3cret#");
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass().getName(), "java.net.UnknownHostException");
        }

    }

}