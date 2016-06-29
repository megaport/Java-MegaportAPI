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
import java.util.*;

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
        environments.put(Environment.STAGING, "https://api-staging.megaport.com");
    }

    /**
     * Constructor for Megaport API session
     * @param environment
     * @param token
     * @throws InvalidCredentialsException
     * @throws UnirestException
     */
    public MegaportApiSession(Environment environment, String token) throws InvalidCredentialsException, UnirestException{
        this.token = token;
        this.server = environments.get(environment);
        login(null, null, token);
    }

    /**
     * Constructor for Megaport API session
     * @param environment
     * @param username
     * @param password
     * @throws InvalidCredentialsException
     * @throws IOException
     * @throws UnirestException
     */
    public MegaportApiSession(Environment environment, String username, String password) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = environments.get(environment);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server
     * @param username
     * @param password
     * @throws InvalidCredentialsException
     * @throws IOException
     * @throws UnirestException
     */
    public MegaportApiSession(String server, String username, String password) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server
     * @param token
     * @throws InvalidCredentialsException
     * @throws IOException
     * @throws UnirestException
     */
    public MegaportApiSession(String server, String token) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        login(null, null, token);
    }

    /**
     * Validates token and server for session
     * @return
     */
    public Boolean isValid(){
        return token != null && server != null;
    }

    /**
     * Validate server details for session.
     * @param server
     * @return The validated server
     * @throws UnreachableHostException
     * @throws IOException
     */
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

    /**
     * Login method
     * @param username
     * @param password
     * @param token
     * @throws InvalidCredentialsException
     * @throws UnirestException
     */
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
            if (data == null) {
                System.out.println(json);
            }
            this.token = (String) data.get("session");
        } else {
            throw new InvalidCredentialsException("Login failed");
        }

    }

    /**
     * This will return the list of Ports owned by the logged-in user.
     * @return {@link List} of {@link MegaportServiceDto}
     * @throws Exception
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
     * @return {@link List} of {@link PartnerPortDto}
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
     * @return {@link AzurePortsDto} of the primary and secondary ExpressRoute ports
     * @throws Exception
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

    /**
     * This invokes the validation of the order details provided
     * @param megaportServiceDtos
     * @return {@link List} of valid {@link ServiceLineItemDto}
     * @throws Exception
     */
    public List<ServiceLineItemDto> validateOrder(List<MegaportServiceDto> megaportServiceDtos) throws Exception{

        String url = server + "/v2/networkdesign/validate";
        HttpResponse<JsonNode> response = Unirest.post(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(megaportServiceDtos)).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, ServiceLineItemDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * Place an order
     * @param megaportServiceDtos
     * @return Returns the response for placing an order
     * @throws Exception
     */
    public String placeOrder(List<MegaportServiceDto> megaportServiceDtos) throws Exception{

        String url = server + "/v2/networkdesign/buy";
        HttpResponse<JsonNode> response = Unirest.post(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(megaportServiceDtos)).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJson(json).get("data").toString();
        } else {
            throw handleError(response);
        }
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

    /**
     * Find a list of available IX services for a given location.
     * @return
     * @throws Exception
     */
    public List<IxDto> findIxForLocation(Integer locationId) throws Exception{
        String url = server + "/v2/product/ix/types";
        HttpResponse<JsonNode> response = Unirest.get(url).header("X-Auth-Token", token).queryString("locationId", locationId).asJson();
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, IxDto.class);
        } else {
            throw handleError(response);
        }
    }

    public void modifyVxcOrCxc(VxcServiceModificationDto dto) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name", dto.getProductName());
        fieldMap.put("aEndVlan", dto.getaEndVlan());
        fieldMap.put("bEndVlan", dto.getbEndVlan());
        fieldMap.put("rateLimit", dto.getRateLimit());

        String url = server + "/v2/product/vxc/" + dto.getProductUid();
        HttpResponse<JsonNode> response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        if (response.getStatus() != 200){
            throw handleError(response);
        }
    }

    public void modifyIx(IxServiceModificationDto dto) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name", dto.getProductName());
        fieldMap.put("rateLimit", dto.getRateLimit());
        fieldMap.put("vlan", dto.getVlan());
        fieldMap.put("macAddress", dto.getMacAddress());
        fieldMap.put("asn", dto.getAsn());

        String url = server + "/v2/product/ix/" + dto.getProductUid();
        HttpResponse<JsonNode> response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        if (response.getStatus() != 200){
            throw handleError(response);
        }
    }

    public void modifyPort(String name, String productUid) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name", name);

        String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        if (response.getStatus() != 200){
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

    /**
     * Find a service usage
     * @param productUid
     * @param from
     * @param to
     * @return {@link GraphDto} of service usage
     * @throws Exception
     */
    public GraphDto findServiceUsage(String productUid, Date from, Date to) throws Exception{
        String url = server + "/v2/graph/mbps";
        HttpResponse<JsonNode> response;
        if (from != null && to != null){
            response = Unirest.get(url).header("X-Auth-Token", token).queryString("productIdOrUid", productUid).queryString("from", from.getTime()).queryString("to", to.getTime()).asJson();
        } else {
            response = Unirest.get(url).header("X-Auth-Token", token).queryString("productIdOrUid", productUid).asJson();
        }
        if (response.getStatus() == 200){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, GraphDto.class);
        } else {
            throw handleError(response);
        }
    }

    /**
     * Invoke a lifecycle action
     * @param productUid
     * @param action
     * @throws Exception
     */
    public void lifecycle(String productUid, LifecycleAction action) throws Exception{
        String url = server + "/v2/product/" + productUid + "/action/" + action.toString();
        HttpResponse<JsonNode> response = Unirest.put(url).header("X-Auth-Token", token).asJson();
        if (response.getStatus() != 200){
            throw handleError(response);
        }
    }

    /**
     * Get a token for the session
     * @return The session token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param response
     * @return The exception being handled
     * @throws InvalidCredentialsException
     * @throws BadRequestException
     */
    private Exception handleError(HttpResponse<JsonNode> response) throws InvalidCredentialsException, BadRequestException{
        if (response.getStatus() == 401){
            return new InvalidCredentialsException("Login failed - your session may have expired");
        } else if (response.getStatus() == 403) {
            HashMap<String, Object> responseMap = JsonConverter.fromJson(response.getBody().toString());
            String message = (String) responseMap.get("message");
            if (message == null) {
                return new BadRequestException(response.getBody().toString());
            } else {
                return new BadRequestException(message);
            }
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
