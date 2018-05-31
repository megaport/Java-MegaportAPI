package com.megaport.api.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.megaport.api.dto.*;
import com.megaport.api.exceptions.*;
import com.megaport.api.util.JsonConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private static final String NETWORK_ERROR = "Network Error";
    private static final String SYSTEM_ERROR = "System Error";
    private static final String VALIDATION_ERROR = "Validation Error";
    private static final String MESSAGE = "message";
    private static final String DATA = "data";

    {
        environments.put(Environment.PRODUCTION, "https://api.megaport.com");
        environments.put(Environment.TRAINING, "https://api-training.megaport.com");
        environments.put(Environment.LOCALHOST, "http://localhost:8080");
        environments.put(Environment.STAGING, "https://api-staging.megaport.com");
        environments.put(Environment.KENOBI, "https://api-kenobi.megaport.com");
        environments.put(Environment.YODA, "https://api-yoda.megaport.com");
        environments.put(Environment.SKYWALKER, "https://api-skywalker.megaport.com");
        environments.put(Environment.JABBA, "https://api-jabba.megaport.com");
        environments.put(Environment.QA, "https://api-qa.megaport.com");
    }

    /**
     * Constructor for Megaport API session
     * @param environment The environment to be targeted e.g. TRAINING
     * @param token The token to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws UnirestException Report any REST specific exceptions
     */
    public MegaportApiSession(Environment environment, String token) throws InvalidCredentialsException, UnirestException{
        this.token = token;
        this.server = environments.get(environment);
        Unirest.setTimeouts(10000, 60000);
        login(null, null, token);
    }

    /**
     * Constructor for Megaport API session
     * @param environment The environment to be targeted e.g. TRAINING
     * @param token The token to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws UnirestException Report any REST specific exceptions
     */
    public MegaportApiSession(Environment environment, String token, Long timeout) throws InvalidCredentialsException, UnirestException{
        this.token = token;
        this.server = environments.get(environment);
        Unirest.setTimeouts(10000, timeout == null ? 60000 : timeout);
        login(null, null, token);
    }

    /**
     * Constructor for Megaport API session
     * @param environment The environment to be targeted e.g. TRAINING
     * @param username The username to use for secure auth
     * @param password The password to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws IOException Report any IO exceptions
     * @throws UnirestException To report any REST specific exceptions
     */
    public MegaportApiSession(Environment environment, String username, String password) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = environments.get(environment);
        Unirest.setTimeouts(10000, 60000);
        login(username, password, null);
    }

    /**
     * Constructor for Megaport API session
     * @param environment The environment to be targeted e.g. TRAINING
     * @param username The username to use for secure auth
     * @param password The password to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws IOException Report any IO exceptions
     * @throws UnirestException To report any REST specific exceptions
     */
    public MegaportApiSession(Environment environment, String username, String password, Long timeout) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = environments.get(environment);
        Unirest.setTimeouts(10000, timeout == null ? 60000 : timeout);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server The server name of the environment to be targeted
     * @param username The username to use for secure auth
     * @param password The password to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws IOException Report any IO exceptions
     * @throws UnirestException Report any REST specific exceptions
     */
    public MegaportApiSession(String server, String username, String password) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        Unirest.setTimeouts(10000, 60000);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server The server name of the environment to be targeted
     * @param username The username to use for secure auth
     * @param password The password to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws IOException Report any IO exceptions
     * @throws UnirestException Report any REST specific exceptions
     */
    public MegaportApiSession(String server, String username, String password, Long timeout) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        Unirest.setTimeouts(10000, timeout == null ? 60000 : timeout);
        login(username, password, null);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server The server name of the environment to be targeted
     * @param token The token to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws IOException Report any IO exceptions
     * @throws UnirestException Report any REST specific exceptions
     */
    public MegaportApiSession(String server, String token, Long timeout) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        Unirest.setTimeouts(10000, timeout == null ? 60000 : timeout);
        login(null, null, token);
    }

    /**
     * Use this only if you get instruction from Megaport support.
     * @param server The server name of the environment to be targeted
     * @param token The token to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws IOException Report any IO exceptions
     * @throws UnirestException Report any REST specific exceptions
     */
    public MegaportApiSession(String server, String token) throws InvalidCredentialsException, IOException, UnirestException {
        this.server = validateServer(server);
        Unirest.setTimeouts(10000, 60000);
        login(null, null, token);
    }

    /**
     * Validates token and server for session
     * @return Boolean true if the server is validated, else false
     */
    public Boolean isValid(){
        return token != null && server != null;
    }

    /**
     * Validate server details for session.
     * @param server The server name of the environment to be targeted
     * @return The validated server
     * @throws UnreachableHostException Reporting exception if host is incorrect
     * @throws IOException Report any IO exceptions
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
     * @param username The username to use for secure auth
     * @param password The password to use for secure auth
     * @param token The token to use for secure auth
     * @throws InvalidCredentialsException Reporting invalid credentials
     * @throws UnirestException Report any REST specific exceptions
     */
    private void login(String username, String password, String token) throws InvalidCredentialsException, UnirestException{

        HttpResponse<JsonNode> response;
        try {
            if (token == null) {
                response = Unirest.post(server + "/v2/login").field("username", username).field("password", password).asJson();
            } else {
                response = Unirest.post(server + "/v2/login/" + token).asJson();
            }
        } catch (UnirestException e){
            throw new ServiceUnavailableException("API Server is not available", 503, null);
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
            throw new InvalidCredentialsException("Login failed", 401, null);
        }

    }

    /**
     * This will return the list of Ports owned by the logged-in user.
     * @return a list of Megaport services
     * @throws Exception Report any exception finding ports
     */
    public List<MegaportServiceDto> findPorts() throws Exception{

        String url = server + "/v2/products";
        HttpResponse<JsonNode> responseJson = null;
        try {
            GetRequest response = Unirest.get(url).header("X-Auth-Token", token);
            responseJson = response.asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (responseJson.getStatus() >= 200 && responseJson.getStatus() < 400){
            String json = responseJson.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, MegaportServiceDto.class);
        } else {
            throw handleError(responseJson);
        }

    }

    /**
     * This will return a list of Port services owned by other Megaport users; you can order a VXC to any of these ports.
     * @return a list of partner Megaport services
     * @throws Exception Report any exception finding partner ports
     */
    public List<PartnerPortDto> findPartnerPorts() throws Exception{

        String url = server + "/v2/dropdowns/partner/megaports";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, PartnerPortDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * Given your Azure service key, obtained from Microsoft ExpressRoute, this end point will return the primary and secondary ExpressRoute ports.
     * @param serviceKey Unique key from the Azure provider to provision a specific network design
     * @return the primary and secondary ExpressRoute ports
     * @throws Exception Report any exception finding Azure ports
     */
    public AzurePortsDto findAzurePorts(String serviceKey) throws Exception {

        String url = server + "/v2/secure/azure/" + serviceKey;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, AzurePortsDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * Given your Google pairing key, this end point will return the valid Google.
     * @param serviceKey Unique key from the Google, in the form {uuid}/{part2}/{part3}, eg 00028caf-7b17-45f1-a43c-1a9cf2f32e75/us-west1/zone1
     * @return list of permitted bandwidths, and ports
     * @throws Exception Report any exception finding Google CGI ports
     */
    public GooglePortsDto findGooglePorts(String serviceKey) throws Exception {

        String url = server + "/v2/secure/google/" + serviceKey;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, GooglePortsDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * Given your Oracle service key, obtained from Microsoft ExpressRoute, this end point will return the primary and secondary ExpressRoute ports.
     * @param oracleCircuitId Unique key from the Azure provider to provision a specific network design
     * @return the primary and secondary ExpressRoute ports
     * @throws Exception Report any exception finding Azure ports
     */
    public CspPortsDto findOraclePorts(String oracleCircuitId) throws Exception {

        String url = server + "/v2/secure/oracle/" + oracleCircuitId;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, CspPortsDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * This invokes the validation of the order details provided
     * @param megaportServiceDtos a list of orders to be validated
     * @return a list of service order details
     * @throws Exception Report any exception validating the orders
     */
    public List<ServiceLineItemDto> validateOrder(List<MegaportServiceDto> megaportServiceDtos) throws Exception{

        String url = server + "/v2/networkdesign/validate";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(megaportServiceDtos)).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, ServiceLineItemDto.class);
        } else {
            throw handleError(response);
        }

    }

    /**
     * Place an order
     * @param megaportServiceDtos a list of Megaport service orders
     * @return Returns a JSON String response when placing an order
     * @throws Exception Report any exception when placing an order
     */
    public String placeOrder(List<MegaportServiceDto> megaportServiceDtos) throws Exception{

        String url = server + "/v2/networkdesign/buy";
        HttpResponse<JsonNode> response = null;
        try {
            Unirest.setTimeouts(0,0);
            response = Unirest.post(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(megaportServiceDtos)).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJson(json).get("data").toString();
        } else {
            throw handleError(response);
        }
    }

    /**
     * Find a list of port locations.
     * @return a list of port locations
     * @throws Exception Report any exception finding port locations
     */
    public List<PortLocationDto> findPortLocations() throws Exception{
        String url = server + "/v2/locations";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, PortLocationDto.class);
        } else {
            throw handleError(response);
        }
    }

    /**
     * Simulate APIserver being unavailable. The load balancer should return 404 in this case...
     * @throws Exception Report any exception finding port locations
     */
    public void simulateServiceUnavailable() throws Exception{
        String url = server + "/doesnotexist";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        throw handleError(response);
    }

    /**
     * Find a list of available IX services for a given location.
     * @param locationId id of the location to search
     * @return a list of IX locations
     * @throws Exception Report any exception finding IX for a specific location
     */
    public List<IxDto> findIxForLocation(Integer locationId) throws Exception{
        String url = server + "/v2/product/ix/types";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).queryString("locationId", locationId).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, IxDto.class);
        } else {
            throw handleError(response);
        }
    }

    /**
     * Method to modify VXC or CXC service
     * @param dto VXC service to modify
     * @throws Exception Report any exception during service modification
     */
    public void modifyVxcOrCxc(VxcServiceModificationDto dto) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        if (dto.getProductName() != null) fieldMap.put("name", dto.getProductName());
        if (dto.getaEndVlan() != null) fieldMap.put("aEndVlan", dto.getaEndVlan());
        if (dto.getbEndVlan() != null) fieldMap.put("bEndVlan", dto.getbEndVlan());
        if (dto.getRateLimit() != null) fieldMap.put("rateLimit", dto.getRateLimit());

        String url = server + "/v2/product/vxc/" + dto.getProductUid();
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() >= 400){
            throw handleError(response);
        }
    }

    /**
     *
     * @param vxcOrderUid
     * @param isApproved
     * @param bEndVlan
     * @throws Exception
     */
    public void approveVxc(String vxcOrderUid, Boolean isApproved, Integer bEndVlan) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("isApproved", isApproved);
        fieldMap.put("vlan", bEndVlan);

        String url = server + "/v2/order/vxc/" + vxcOrderUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() >= 400){
            throw handleError(response);
        }
    }

    /**
     * Method to modify IX service
     * @param dto IX service to modify
     * @throws Exception Report any exception during service modification
     */
    public void modifyIx(IxServiceModificationDto dto) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        if (dto.getProductName() != null) fieldMap.put("name", dto.getProductName());
        if (dto.getRateLimit() != null) fieldMap.put("rateLimit", dto.getRateLimit());
        if (dto.getVlan() != null) fieldMap.put("vlan", dto.getVlan());
        if (dto.getMacAddress() != null) fieldMap.put("macAddress", dto.getMacAddress());
        if (dto.getAsn() != null) fieldMap.put("asn", dto.getAsn());

        String url = server + "/v2/product/ix/" + dto.getProductUid();
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() >= 400){
            throw handleError(response);
        }
    }

    /**
     * Method to modify service port
     * @param name New name of port
     * @param productUid Unique product id of existing port to be modified
     * @throws Exception Report any exception during service port modification
     */
    public void modifyPort(String name, String productUid) throws Exception{

        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name", name);

            String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() >= 400){
            throw handleError(response);
        }
    }

    /**
     * Method to modify service port attributes
     * @param fieldMap New name of port
     * @param productUid Unique product id of existing port to be modified
     * @throws Exception Report any exception during service port modification
     */
    public void modifyPort(Map<String,Object> fieldMap, String productUid) throws Exception{

        String url = server + "/v2/product/megaport/" + productUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.put(url).header("X-Auth-Token", token).header("Content-Type", "application/json").body(JsonConverter.toJson(fieldMap)).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() >= 400){
            throw handleError(response);
        }
    }

    public Object findServiceDetail(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            HashMap<String, Object> map = JsonConverter.fromJsonDataAsMap(json);
            String productType = (String) map.get("productType");
            switch (productType.toLowerCase()) {
                case "vxc":
                    return JsonConverter.fromJsonDataAsObject(json, VxcServiceDto.class);

                case "cxc":
                    return JsonConverter.fromJsonDataAsObject(json, VxcServiceDto.class);

                case "megaport":
                    return JsonConverter.fromJsonDataAsObject(json, MegaportServiceDto.class);

                case "ix":
                    return JsonConverter.fromJsonDataAsObject(json, IxServiceDto.class);

                default:
                    throw new RuntimeException("Could not determine the product type from [" + productType + "]");
            }
        } else {
            throw handleError(response);
        }
    }

    public MegaportServiceDto findServiceDetailMegaport(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, MegaportServiceDto.class);
        } else {
            throw handleError(response);
        }
    }

    public VxcServiceDto findServiceDetailVxc(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, VxcServiceDto.class);
        } else {
            throw handleError(response);
        }
    }

    public IxServiceDto findServiceDetailIx(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, IxServiceDto.class);
        } else {
            throw handleError(response);
        }
    }

    public List<ActiveLogDto> findServiceLogs(String productUid) throws Exception{
        String url = server + "/v2/product/" + productUid + "/logs";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, ActiveLogDto.class);
        } else {
            throw handleError(response);
        }
    }

    public List<CompanyDto> findPartnerCustomers() throws Exception{
        String url = server + "/v2/partner/customers";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, CompanyDto.class);
        } else {
            throw handleError(response);
        }
    }

    public List<MegaportServiceDto> findPartnerCustomersProducts() throws Exception{
        String url = server + "/v2/partner/customers/products";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url).header("X-Auth-Token", token).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsList(json, MegaportServiceDto.class);
        } else {
            throw handleError(response);
        }
    }

    /**
     * Find a service usage
     * @param productUid Unique product id of existing service
     * @param from Date from range
     * @param to Date to range
     * @return {@link GraphDto} of service usage
     * @throws Exception Report any exceptions from searching service usage
     */
    public GraphDto findServiceUsage(String productUid, Date from, Date to) throws Exception{
        String url = server + "/v2/graph/mbps";
        HttpResponse<JsonNode> response;
        try {
            if (from != null && to != null){
                response = Unirest.get(url).header("X-Auth-Token", token).queryString("productIdOrUid", productUid).queryString("from", from.getTime()).queryString("to", to.getTime()).asJson();
            } else {
                response = Unirest.get(url).header("X-Auth-Token", token).queryString("productIdOrUid", productUid).asJson();
            }
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, GraphDto.class);
        } else {
            throw handleError(response);
        }
    }

    /**
     * This feature is Experimental! You will be responsible for ALL charges resulting from using this code!
     * For a post-paid service, check the actual speed for the specified number of hours,
     * and set the speed to be a nominated margin (in Mbps) above the max speed (greater of in/out), and within the floor and ceiling speeds
     * @param productUid Unique product id of existing service
     * @param margin margin in Mbps
     * @param measurementHours Number of hours
     * @param floor Floor speed
     * @param ceiling Ceiling speed
     * @throws Exception Report any exceptions doing auto speed update
     */
    public void autoSpeedUpdate(String productUid, Integer measurementHours, Integer margin, Integer floor, Integer ceiling) throws Exception{

        System.out.println("This feature is Experimental! You will be responsible for ALL charges resulting from using this code!");

        Object serviceObject = findServiceDetail(productUid);

        Boolean isPostPaid = false;
        Boolean isVxcOrIx = false;
        Boolean customerOwnsBothEnds = false;

        VxcServiceDto vxcServiceDto = null;
        IxServiceDto ixServiceDto = null;

        if (serviceObject instanceof VxcServiceDto){
            isVxcOrIx = true;
            vxcServiceDto = (VxcServiceDto) serviceObject;
            if (vxcServiceDto.getUsageAlgorithm().equals(UsageAlgorithm.TIME_AT_RATE)) isPostPaid = true;
            if (vxcServiceDto.getaEnd().getOwnerUid().equals(vxcServiceDto.getbEnd().getOwnerUid())) customerOwnsBothEnds = true;
        } else if (serviceObject instanceof IxServiceDto) {
            isVxcOrIx = true;
            ixServiceDto = (IxServiceDto) serviceObject;
            if (ixServiceDto.getUsageAlgorithm().equals(UsageAlgorithm.TIME_AT_RATE)) isPostPaid = true;
        }

        if (isPostPaid && isVxcOrIx){

            Date now = new Date();
            Date hourAgo = new Date(now.getTime() - 1000 * 60 * 60 * measurementHours);

            GraphDto serviceUsage = findServiceUsage(productUid, hourAgo, now);

            Double maxIn = 1.0;
            Double maxOut = 1.0;

            for(Double in : serviceUsage.getIn_mbps()){
                if (in > maxIn) maxIn = in;
            }

            for(Double out : serviceUsage.getOut_mbps()){
                if (out > maxOut) maxOut = out;
            }

            Double max = maxIn;
            if (max < maxOut) max = maxOut;

            Double newSpeed;
            if (max < floor){
                newSpeed = floor.doubleValue();
            } else if (max > ceiling) {
                newSpeed = ceiling.doubleValue();
            } else {
                newSpeed = max + margin.doubleValue();
            }

            System.out.println("Recent speed maxes out at [" + max + "], so about to update the speed to [" + newSpeed + "] - you will be on the hook for any charges resulting from this!");

            if (vxcServiceDto != null){
                if (customerOwnsBothEnds) {
                    VxcServiceModificationDto dto = new VxcServiceModificationDto();
                    dto.setProductUid(productUid);
                    dto.setRateLimit(newSpeed.intValue());
                } else {
                    throw new RuntimeException("You have to own both ends of a VXC to automate speed changes");
                }
            } else {
                IxServiceModificationDto dto = new IxServiceModificationDto();
                dto.setRateLimit(newSpeed.intValue());
                dto.setProductUid(productUid);
                modifyIx(dto);
            }

        } else {
            throw new RuntimeException("This is only intended for post-paid VXC and IX services");
        }



    }

    /**
     * Invoke a lifecycle action
     * @param productUid Unique product id of existing service
     * @param action the action to invoke
     * @param terminationDate only required for cancel action
     * @throws Exception Report any exceptions during lifecycle action
     */
    public void lifecycle(String productUid, LifecycleAction action, Date terminationDate) throws Exception{
        String url = server + "/v2/product/" + productUid + "/action/" + action.toString();
        HttpResponse<JsonNode> response;

        try {
            if (terminationDate == null) {
                response = Unirest.post(url).header("X-Auth-Token", token).asJson();
            } else {
                response = Unirest.post(url).header("X-Auth-Token", token).header("Content-Type", "application/json").queryString("terminationDate", terminationDate.getTime()).asJson();
            }
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() >= 400){
            throw handleError(response);
        }
    }



    //https://api.megaport.com/v2//pricebook/megaport?locationId=163&speed=10000&term=1&buyoutPort=&productUid=226cdf40-2e4d-11e8-b2c8-29215dd8231a&virtual=

    /**
     *
     * @param locationId
     * @param speed
     * @param term
     * @param virtual
     * @return
     * @throws Exception
     */
    public PriceDto findMegaportPrice(Integer locationId, Integer speed, Integer term, Boolean virtual) throws Exception{
        String url = server + "/v2/pricebook/megaport";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url)
                                .header("X-Auth-Token", token)
                                .queryString("locationId", locationId)
                                .queryString("speed", speed)
                                .queryString("virtual", virtual)
                                .queryString("term", term).asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, PriceDto.class);
        } else {
            throw handleError(response);
        }
    }


    /**
     *
     * @param aLocationId
     * @param bLocationId
     * @param speed
     * @param connectType - DEFAULT for point to point VXC to own port, or another customer's port. For Cloud Service Providers choose one of:
         AMSIX
         AZURE
         VROUTER
         GOOGLE
         WEBAIR
         AWS
         ORACLE
         SFDC
         ALIBABA
     * @return
     * @throws Exception
     */
    public PriceDto findVxcPrice(Integer aLocationId, Integer bLocationId, Integer speed, String connectType) throws Exception{
        String url = server + "/v2/pricebook/vxc";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url)
                                .header("X-Auth-Token", token)
                                .queryString("aLocationId", aLocationId)
                                .queryString("bLocationId", bLocationId)
                                .queryString("speed", speed)
                                .queryString("connectType", connectType)
                                .asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, PriceDto.class);
        } else {
            throw handleError(response);
        }
    }

    //https://api.megaport.com/v2//pricebook/ix?ixType=Ashburn%20IX&portLocationId=90&speed=100
    /**
     * @param portLocationId
     * @param speed
     * @param ixType - The name of the IX. These can be found from calling findIxForLocation()
     * @return PriceDto
     * @throws Exception
     */
    public PriceDto findIxPrice(Integer portLocationId, String ixType, Integer speed) throws Exception{
        String url = server + "/v2/pricebook/ix";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url)
                                .header("X-Auth-Token", token)
                                .queryString("portLocationId", portLocationId)
                                .queryString("speed", speed)
                                .queryString("ixType", ixType)
                                .asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsObject(json, PriceDto.class);
        } else {
            throw handleError(response);
        }
    }

    /**
     * The purpose of this method is to estimate the charges for the given year / month, including the effect of a speed change.
     *
     * @param productUid
     * @param year
     * @param month
     * @param newSpeed
     * @return
     * @throws Exception
     */
    public PriceCheckDto speedChangePriceCheck(String productUid, Integer year, Integer month, Integer newSpeed) throws Exception{
        String url = server + "/v2/product/" + productUid + "/rating/" + year + "/" + month;
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url)
                                .header("X-Auth-Token", token)
                                .queryString("speed", newSpeed)
                                .asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            HashMap<String, Object> dataMap = JsonConverter.fromJsonDataAsMap(json);
            PriceCheckDto dto = new PriceCheckDto();
            dto.setCurrency(dataMap.get("currency").toString());
            dto.setDelta(new BigDecimal(dataMap.get("delta").toString()));
            dto.setTotal(new BigDecimal(dataMap.get("total").toString()));
            dto.setLongTermMonthly(new BigDecimal(dataMap.get("longTermMonthly").toString()));
            return dto;
        } else {
            throw handleError(response);
        }
    }

    /**
     *
     * The purpose of this method is to check for the availability of Vlans on a given port.  If the vlan you ask for is available, then it is returned to you in a list of one element.  If the vlan is not available, then a list of 3-5 other available vlans is provided. If a port has been set to untagged, then no vlans are available on it.
     *
     * @param productUid
     * @param vlan
     * @return a list of available Vlans
     * @throws Exception
     */
    public List<Integer> validateVlan(String productUid, Integer vlan) throws Exception{
        String url = server + "/v2/product/port/" + productUid + "/vlan";
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(url)
                                .header("X-Auth-Token", token)
                                .queryString("vlan", vlan)
                                .asJson();
        } catch (UnirestException e) {
            throw new ServiceUnavailableException("API Server is not available", 503, null);
        }
        if (response.getStatus() < 400){
            String json = response.getBody().toString();
            return JsonConverter.fromJsonDataAsSimpleList(json, Integer.class);
        } else {
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

        StringBuilder data = new StringBuilder();

        HashMap<String, Object> responseMap = new HashMap<>();
        if (response.getBody() != null) {
            try {
                responseMap = JsonConverter.fromJson(response.getBody().toString());
            } catch (Exception e) {
                // if we can't convert this to JSON, then bad stuff has happened, and we can really ony return a 500
                System.out.println(response.getBody().toString());
                return new ServerErrorException(NETWORK_ERROR, 500, null);
            }
        } else {
            // null response, so 500 time again...
            return new ServerErrorException(NETWORK_ERROR, 500, null);
        }

        String message = (String) responseMap.get(MESSAGE);
        Object tempData = responseMap.get(DATA);

        switch (response.getStatus()){

            case 401:
                return new InvalidCredentialsException("Login failed - your session may have expired", 401, null);

            case 403:
                return new UnauthorizedException("You don't have permission for this operation - do you own the service in question?", 403, null);

            case 404:
                return new ServiceUnavailableException("API Server is not available", 503, null);

            case 503:
                return new ServiceUnavailableException("API Server is not available", 503, null);

            case 400:

                if (tempData != null && tempData instanceof List && !(((List) tempData).isEmpty())) {
                    HashMap<String, Object> errorReponseMap = (HashMap<String, Object>) ((List) tempData).get(0);
                    for(Map.Entry<String, Object> entry : errorReponseMap.entrySet()) {
                        String value;
                        if (entry.getValue() instanceof String) {
                            value =  (String) entry.getValue();
                        } else {
                            value = JsonConverter.toJson(entry.getValue());
                        }
                        data.append(" ").append(value).append("\n");
                    }
                } else {
                    if (tempData != null) {
                        data.append(responseMap.get(DATA).toString());
                    }
                }

                if (StringUtils.isEmpty(message)) {
                    return new BadRequestException(NETWORK_ERROR, 500, null);
                } else {
                    // this is the usual case, but we need to filter out 'unusual' validation messages, and only pass on the usual suspects
                    String responseData = filter(data.toString());
                    return new BadRequestException(message + (StringUtils.isEmpty(responseData) ? "" : " - " + responseData.replace(" API: ","")), 400, null);
                }

            // general fault we don't really know about
            default:
                return new ServerErrorException(SYSTEM_ERROR, 500, null);

        }

    }

    /**
     * The idea here is to only allow user-friendly messages though, and mask unknown messages...
     * The following are 3 months worth of production validation failures
     *
     "API: Error provisioning IX service",
     "API: Error provisioning MEGAPORT service",
     "API: Error provisioning VXC service",
     "API: Service object validation failed for VXC",
     "AWS requires an ASN",
     "DB execute failed ERROR:  MAC address already on this VPLS\n(INSERT INTO n_vpls_interface (interface_id, vpls_id, mac_address, vlan, rate_limit_mbps) VALUES (?, ?, ?, ?, ?) RETURNING id)\n\n",
     "Duplicate IP Network",
     "Failed to create new VPLSInterface",
     "Interface 10971 already has Auckland IX, and only one is permitted"
     "Invalid Amazon IP address",
     "Invalid ASN",
     "Invalid AWS account number 459329291",
     "Invalid customer IP address",
     "Invalid service_key length '6543'",
     "Invalid subnet mask",
     "Invalid virtual interface type",
     "IP addresses must be in the same network",
     "Missing AWS object",
     "Missing Azure object",
     "Prefix (10.0.1.0/24) must be a publicly routable network",
     "Prefix (203.56.233.124/32) must be a /31 or shorter",
     "Prefix list must contain the point-to-point subnet",
     "VLAN 4001 not available on MEGAPORT service 5088",
     "VXC goes to an Azure port that is already taken",
     "VXC VLAN doesn't match Azure service_key",
     * @param data
     * @return
     */
    private String filter(String data) {

        if (data.contains("DB execute failed ERROR")){
            return "There was a problem validating this order";
        } else return data;

    }

}
