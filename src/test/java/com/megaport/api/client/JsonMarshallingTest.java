package com.megaport.api.client;

import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.VxcServiceDto;
import com.megaport.api.util.JsonConverter;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by awells on 10/2/17.
 */
public class JsonMarshallingTest {

    @Test
    public void testMarshallingVXCServiceDto(){
        String json = "    {\n" +
                        "      \"rateLimit\": 10000,\n" +
                        "      \"usageAlgorithm\": \"POST_PAID_HOURLY_SPEED\",\n" +
                        "      \"vxcApproval\": {\n" +
                        "        \"uid\": null,\n" +
                        "        \"newSpeed\": null,\n" +
                        "        \"message\": null,\n" +
                        "        \"type\": null,\n" +
                        "        \"status\": null\n" +
                        "      },\n" +
                        "      \"productName\": \"Test1\",\n" +
                        "      \"nServiceId\": 13847,\n" +
                        "      \"bEnd\": {\n" +
                        "        \"vlan\": 3612,\n" +
                        "        \"locationId\": 66,\n" +
                        "        \"location\": \"Equinix SV1\",\n" +
                        "        \"ownerUid\": \"605cb850-dfb4-4a05-a171-8bf17757b3a2\",\n" +
                        "        \"productUid\": \"4d39ff56-8e8d-4a34-9154-0ec0631696ed\",\n" +
                        "        \"productName\": \"AWS (us-west-1)\"\n" +
                        "      },\n" +
                        "      \"createdBy\": null,\n" +
                        "      \"attributeTags\": {\n" +
                        "        \n" +
                        "      },\n" +
                        "      \"provisioningStatus\": \"DEPLOYABLE\",\n" +
                        "      \"aEnd\": {\n" +
                        "        \"vlan\": 306,\n" +
                        "        \"locationId\": 60,\n" +
                        "        \"location\": \"CoreSite LA1\",\n" +
                        "        \"ownerUid\": \"160208ae-01e4-4cb9-8d57-03a197bd47a8\",\n" +
                        "        \"productUid\": \"88d69d06-095d-4865-b97f-3d8445baac57\",\n" +
                        "        \"productName\": \"T5 Test\"\n" +
                        "      },\n" +
                        "      \"distanceBand\": \"INTERCAP\",\n" +
                        "      \"productUid\": \"3c501040-a71f-4a6a-a0a0-9ae1978f32ff\",\n" +
                        "      \"productType\": \"VXC\",\n" +
                        "      \"createDate\": 1486668245531\n" +
                        "    }\n";

        VxcServiceDto dto = JsonConverter.fromJson(json, VxcServiceDto.class);
        assertTrue(dto != null);


    }

    @Test
    public void testMarshallingMegaportServiceDto(){
        String json = "{\n" +
                "  \"virtual\": false,\n" +
                "  \"associatedVxcs\": [\n" +
                "    {\n" +
                "      \"rateLimit\": 10000,\n" +
                "      \"usageAlgorithm\": \"POST_PAID_HOURLY_SPEED\",\n" +
                "      \"vxcApproval\": {\n" +
                "        \"uid\": null,\n" +
                "        \"newSpeed\": null,\n" +
                "        \"message\": null,\n" +
                "        \"type\": null,\n" +
                "        \"status\": null\n" +
                "      },\n" +
                "      \"productName\": \"Test1\",\n" +
                "      \"nServiceId\": 13847,\n" +
                "      \"bEnd\": {\n" +
                "        \"vlan\": 3612,\n" +
                "        \"locationId\": 66,\n" +
                "        \"location\": \"Equinix SV1\",\n" +
                "        \"ownerUid\": \"605cb850-dfb4-4a05-a171-8bf17757b3a2\",\n" +
                "        \"productUid\": \"4d39ff56-8e8d-4a34-9154-0ec0631696ed\",\n" +
                "        \"productName\": \"AWS (us-west-1)\"\n" +
                "      },\n" +
                "      \"createdBy\": null,\n" +
                "      \"attributeTags\": {\n" +
                "        \n" +
                "      },\n" +
                "      \"provisioningStatus\": \"DEPLOYABLE\",\n" +
                "      \"aEnd\": {\n" +
                "        \"vlan\": 306,\n" +
                "        \"locationId\": 60,\n" +
                "        \"location\": \"CoreSite LA1\",\n" +
                "        \"ownerUid\": \"160208ae-01e4-4cb9-8d57-03a197bd47a8\",\n" +
                "        \"productUid\": \"88d69d06-095d-4865-b97f-3d8445baac57\",\n" +
                "        \"productName\": \"T5 Test\"\n" +
                "      },\n" +
                "      \"distanceBand\": \"INTERCAP\",\n" +
                "      \"productUid\": \"3c501040-a71f-4a6a-a0a0-9ae1978f32ff\",\n" +
                "      \"productType\": \"VXC\",\n" +
                "      \"createDate\": 1486668245531\n" +
                "    }\n" +
                "  ],\n" +
                "  \"companyName\": \"Megaport Lab\",\n" +
                "  \"lagPrimary\": true,\n" +
                "  \"resources\": {\n" +
                "    \"interface\": {\n" +
                "      \"port_speed\": 10000,\n" +
                "      \"supported_speeds\": [\n" +
                "        1000,\n" +
                "        10000\n" +
                "      ],\n" +
                "      \"demarcation\": \"CoreSite LA1\\n624 South Grand Avenue\\nCR2700-118\\nType: Single Mode Fibre Pair\\nTermination: LC connector\\n\\nPlease forward cross connect completion details to activations@megaport.com for prompt service activation\\n\",\n" +
                "      \"name\": \"Ethernet1\\/9\",\n" +
                "      \"resource_type\": \"interface\",\n" +
                "      \"description\": \"13845 | Megaport Lab\",\n" +
                "      \"id\": 5057,\n" +
                "      \"media\": \"LR\\/LX\",\n" +
                "      \"up\": 0\n" +
                "    }\n" +
                "  },\n" +
                "  \"companyUid\": \"160208ae-01e4-4cb9-8d57-03a197bd47a8\",\n" +
                "  \"vxcAutoApproval\": false,\n" +
                "  \"terminateDate\": null,\n" +
                "  \"productName\": \"T5 Test\",\n" +
                "  \"marketplaceVisibility\": true,\n" +
                "  \"market\": \"US\",\n" +
                "  \"lagId\": 7,\n" +
                "  \"createdBy\": \"9031d3c9-87ca-4498-94f3-9d1ba7faedc5\",\n" +
                "  \"attributeTags\": {\n" +
                "    \n" +
                "  },\n" +
                "  \"provisioningStatus\": \"CONFIGURED\",\n" +
                "  \"locationId\": 60,\n" +
                "  \"portSpeed\": 10000,\n" +
                "  \"associatedIxs\": [\n" +
                "    \n" +
                "  ],\n" +
                "  \"buyoutPort\": false,\n" +
                "  \"productUid\": \"88d69d06-095d-4865-b97f-3d8445baac57\",\n" +
                "  \"productType\": \"MEGAPORT\",\n" +
                "  \"createDate\": 1486667546893,\n" +
                "  \"vxcpermitted\": true\n" +
                "}";

        MegaportServiceDto megaportServiceDto = JsonConverter.fromJson(json, MegaportServiceDto.class);
        assertTrue(megaportServiceDto != null);
    }
}
