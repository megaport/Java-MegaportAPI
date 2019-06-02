package com.megaport.api.client;

import com.megaport.api.dto.AzurePortsDto;
import com.megaport.api.dto.Environment;
import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.PartnerPortDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class AzureTest extends SessionHelper {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = getSession();
        assertTrue(session.isValid());

    }

    @Test
    public void testFindExpressRoutePorts() throws Exception{
        AzurePortsDto ports = session.findAzurePorts("197d927b-90bc-4b1b-bffd-fca17a7ec735");
        assertTrue(ports != null);
    }

    @Test
    @Ignore
    public void testOrderAzure() throws Exception {
        String order = "[\n" +
                "  {\n" +
                "    \"productUid\": \"8280965a-87e1-449f-8ca8-bfc22fe4f4cb\",\n" +
                "    \"productName\": \"VRouter link test\",\n" +
                "    \"companyUid\": \"4f185454-f131-4b65-8fc7-be34439e884e\",\n" +
                "    \"companyName\": \"Telx New Portal Original Test Account\",\n" +
                "    \"provisioningStatus\": \"CONFIGURED\",\n" +
                "    \"createDate\": 1495232200222,\n" +
                "    \"portSpeed\": 10000,\n" +
                "    \"terminateDate\": null,\n" +
                "    \"market\": \"US\",\n" +
                "    \"locationId\": 62,\n" +
                "    \"vxcpermitted\": true,\n" +
                "    \"vxcAutoApproval\": false,\n" +
                "    \"marketplaceVisibility\": true,\n" +
                "    \"virtual\": true,\n" +
                "    \"term\": null,\n" +
                "    \"productType\": \"MEGAPORT\",\n" +
                "    \"associatedVxcs\": [\n" +
                "      {\n" +
                "        \"productUid\": null,\n" +
                "        \"productName\": \"TEST L3 AZURE\",\n" +
                "        \"productType\": null,\n" +
                "        \"rateLimit\": 1000,\n" +
                "        \"distanceBand\": null,\n" +
                "        \"provisioningStatus\": null,\n" +
                "        \"aEnd\": {\n" +
                "          \"ownerUid\": null,\n" +
                "          \"productUid\": null,\n" +
                "          \"productName\": null,\n" +
                "          \"locationId\": null,\n" +
                "          \"vlan\": 200,\n" +
                "          \"partnerConfig\": {\n" +
                "            \"interfaces\": [\n" +
                "              {\n" +
                "                \"bgpConnections\": [\n" +
                "                  {\n" +
                "                    \"peerAsn\": 64496,\n" +
                "                    \"localAsn\": 64495,\n" +
                "                    \"peerIpAddress\": \"192.49.2.2\",\n" +
                "                    \"localIpAddress\": \"192.49.2.1\",\n" +
                "                    \"password\": \"secret\"\n" +
                "                  }\n" +
                "                ],\n" +
                "                \"vlan\": 10,\n" +
                "                \"ipAddresses\": [\n" +
                "                  \"192.49.2.1\\/30\"\n" +
                "                ],\n" +
                "                \"natIpAddresses\": [\n" +
                "                  \"192.49.2.1\"\n" +
                "                ]\n" +
                "              }\n" +
                "            ],\n" +
                "            \"connectType\": \"VROUTER\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"bEnd\": {\n" +
                "          \"ownerUid\": null,\n" +
                "          \"productUid\": \"eb53e285-f899-4b6b-9890-5049cb9cd319\",\n" +
                "          \"productName\": null,\n" +
                "          \"locationId\": null,\n" +
                "          \"vlan\": 0,\n" +
                "          \"partnerConfig\": {\n" +
                "            \"serviceKey\": \"00566196-3a45-4824-902d-9511dd3a5a5e\",\n" +
                "            \"connectType\": \"AZURE\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"usageAlgorithm\": null,\n" +
                "        \"partnerConfigs\": {\n" +
                "          \n" +
                "        },\n" +
                "        \"createDate\": null,\n" +
                "        \"terminateDate\": null,\n" +
                "        \"costCentre\": null,\n" +
                "        \"resources\": {\n" +
                "          \n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"associatedIxs\": [\n" +
                "      \n" +
                "    ],\n" +
                "    \"resources\": {\n" +
                "      \"virtual_router\": {\n" +
                "        \n" +
                "      },\n" +
                "      \"interface\": {\n" +
                "        \"id\": 7591,\n" +
                "        \"description\": \"nyc-tx1-vr-1 Port 3\\/eth4\",\n" +
                "        \"demarcation\": \"\",\n" +
                "        \"resource_type\": \"interface\",\n" +
                "        \"name\": \"Ethernet5\\/12\",\n" +
                "        \"supported_speeds\": [\n" +
                "          1000,\n" +
                "          10000\n" +
                "        ],\n" +
                "        \"port_speed\": 10000,\n" +
                "        \"media\": \"LR\\/LX\",\n" +
                "        \"up\": 1\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";
    }

}