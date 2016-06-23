package com.megaport.api.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.megaport.api.dto.*;
import com.megaport.api.exceptions.BadRequestException;
import com.megaport.api.exceptions.InvalidCredentialsException;
import com.megaport.api.exceptions.UnreachableHostException;
import com.megaport.api.util.JsonConverter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class MegaportApiSession {

    private final Map<Environment,String> environments = new HashMap<>();
    private String token = null;
    private String server = null;

    {
        environments.put(Environment.PRODUCTION, "https://api.megaport.com");
        environments.put(Environment.TRAINING, "https://api-training.megaport.com");
        environments.put(Environment.LOCALHOST, "http://localhost:8080");
    }

    public MegaportApiSession(Environment environment, String token) throws InvalidCredentialsException, UnirestException{
        this.token = token;
        this.server = environments.get(environment);
        login(null, null, token);
    }

    public MegaportApiSession(Environment environment, String username, String password) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = environments.get(environment);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server
     * @param username
     * @param password
     */
    public MegaportApiSession(String server, String username, String password) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server
     * @param token
     */
    public MegaportApiSession(String server, String token) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        login(null, null, token);
    }

    public Boolean isValid(){
        return token != null && server != null;
    }

    private String validateServer(String server) throws UnreachableHostException, IOException {

        String host;
        String https = "https://";
        String http = "http://";

        // strip out the
        if (server.contains(https)){
            host = server.substring(https.length());
        } else if (server.contains(http)){
            host = server.substring(http.length());
        } else {
            throw new UnknownHostException("You must include either http:// or https:// prefix, and optionally :<port> suffix in the server name.");
        }

        // strip off the port
        if (host.contains(":")){
            host = host.substring(0, host.indexOf(":"));
        }

        if (InetAddress.getByName(host).getAddress() != null){
            return server;
        } else {
            throw new UnknownHostException("This is not a valid host [" + server + "]");
        }

    }

    private void login(String username, String password, String token) throws InvalidCredentialsException, UnirestException{

        HttpResponse<JsonNode> response;
        if (token == null){
            response = Unirest.post(server + "/v2/login").field("username", username).field("password",password).asJson();
        } else {
            response = Unirest.post(server + "/v2/login/" + token).asJson();
        }

        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            HashMap<String, Object> map = JsonConverter.fromJson(json);
            Map<String,Object> data = (Map<String,Object>) map.get("data");
            this.token = (String) data.get("session");
        } else {
            throw new InvalidCredentialsException("Login failed");
        }

    }

    /**
     * This will return the list of Ports owned by the logged-in user.
     * @return List<MegaportServiceDto>
     * @throws BadRequestException
     * @throws InvalidCredentialsException
     * @throws IOException
     */
    public List<MegaportServiceDto> findPorts() throws Exception{

        String url = server + "/v2/products";
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, MegaportServiceDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * This will return a list of Port services owned by other Megaport users; you can order a VXC to any of these ports.
     * @return
     * @throws Exception
     */
    public List<PartnerPortDto> findPartnerPorts() throws Exception{

        String url = server + "/v2/dropdowns/partner/megaports";
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, PartnerPortDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * Given your Azure service key, obtained from Microsoft ExpressRoute, this end point will return the primary and secondary ExpressRoute ports.
     * @param serviceKey
     * @return
     */
    public AzurePortsDto findAzurePorts(String serviceKey) throws Exception {

        String url = server + "/v2/secure/azure/" + serviceKey;
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, AzurePortsDto.class);
        } else {
            throw handleError(response);
        }

    }

    public List<ServiceLineItemDto> validateOrder(List<MegaportServiceDto> megaportServiceDtos){
        return new ArrayList<>();
    }

    public OrderResponseDto placeOrder(List<MegaportServiceDto> megaportServiceDtos){
        return new OrderResponseDto();
    }

    public List<PortLocationDto> findPortLocations() throws Exception{
        String url = server + "/v2/locations";
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, PortLocationDto.class);
        } else {
            throw handleError(response);
        }
    }

    public TechnicalServiceDto findServiceDetail(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, TechnicalServiceDto.class);
        } else {
            throw handleError(response);
        }
    }

    public List<ActiveLogDto> findServiceLogs(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid + "/logs";
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, ActiveLogDto.class);
        } else {
            throw handleError(response);
        }
    }

    public GraphDto findServiceUsage(String productUid){
        return new GraphDto();
    }

    public String getToken() {
        return token;
    }

    private Exception handleError(HttpResponse<JsonNode> response) throws InvalidCredentialsException, BadRequestException{
        if ((response.getStatus() == 401) || (response.getStatus() == 403)){
            return new InvalidCredentialsException("Login failed - your session may have expired");
        } else if (response.getStatus() == 400) {
            return new BadRequestException(response.getBody().toString());
        } else {
            if (response.getBody() != null && response.getBody().toString() != null){
                return new RuntimeException(response.getBody().toString());
            } else {
                return new RuntimeException("The request failed, please contact Megaport support.");
            }
        }
    }

}
