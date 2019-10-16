package com.megaport.api.client;

import com.megaport.api.dto.Environment;
import kong.unirest.UnirestException;

import java.io.IOException;

public class SessionHelper {
    private final Environment environment = Environment.LOCALHOST;

    MegaportApiSession getSession() throws IOException, UnirestException {
        return new MegaportApiSession(environment, "api.test", "Abc123", null);
    }

    MegaportApiSession getSession(String username, String password) throws IOException, UnirestException {
        return new MegaportApiSession(environment, username, password);
    }

    MegaportApiSession getSession(String token) throws UnirestException {
        return new MegaportApiSession(environment, token);
    }

}
