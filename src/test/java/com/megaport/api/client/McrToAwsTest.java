package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class McrToAwsTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123", null);
        assertTrue(session.isValid());

    }

    @Test
    public void testOrderMcrToAws() throws Exception{

        MegaportServiceDto goodVxc = createGoodVxc();

        String order = session.placeOrder(Collections.singletonList(goodVxc));

        System.out.println(">> MCR to AWS order: " + order);

    }


    /**
     * {
     *   "productUid": "edf1f1c6-b046-48fd-919b-1cd99104f6dd",
     *   "associatedVxcs": [
     *     {
     *       "rateLimit": 100,
     *       "productName": "Example VXC",
     *       "provisioningStatus": "DESIGN",
     *       "aEnd": {
     *         "vlan": null,
     *         "partnerConfig": {
     *           "complete": true,
     *           "awsAuto": true,
     *           "azureAuto": true
     *         }
     *       },
     *       "bEnd": {
     *         "productUid": "5a28d2ef-cfd6-4873-8e2f-be4bc5729a64",
     *         "vlan": null,
     *         "partnerConfig": {
     *           "type": "private",
     *           "name": "Example AWS title",
     *           "asn": "65333",
     *           "ownerAccount": "684021030471",
     *           "customerIpAddress": null,
     *           "amazonIpAddress": null,
     *           "amazonAsn": "65333",
     *           "authKey": null,
     *           "prefixes": null,
     *           "connectType": "AWS"
     *         }
     *       },
     *       "productType": "VXC",
     *       "connectType": "AWS"
     *     }
     *   ],
     *   "associatedIxs": []
     * }
     */
    private MegaportServiceDto createGoodVxc(){

        MegaportServiceDto dto = new MegaportServiceDto();

        // existing MCR service owned by Megaport Lab account
        dto.setProductUid("b17ed7a5-bc3e-42df-9d2e-044dbc324a0f");

        VxcServiceDto vxcDto = new VxcServiceDto();
        vxcDto.setProductType(ProductType.VXC);
        vxcDto.setProductName("My VXC");
        vxcDto.setRateLimit(100);
        vxcDto.setProvisioningStatus(ProvisioningStatus.DESIGN);


        VxcEndDto endA = new VxcEndDto();
        endA.setVlan(null);
        Map<String,Object> optimisedPartnerConfig = new HashMap<>();
        optimisedPartnerConfig.put("complete", true);
        optimisedPartnerConfig.put("awsAuto", true);
        optimisedPartnerConfig.put("azureAuto", true);
        endA.setPartnerConfig(optimisedPartnerConfig);

        VxcEndDto endB = new VxcEndDto();
        endB.setVlan(null);
        endB.setProductUid("5a28d2ef-cfd6-4873-8e2f-be4bc5729a64");
        Map<String,Object> standardAwsPartnerConfig = new HashMap<>();
        standardAwsPartnerConfig.put("type", "private");
        standardAwsPartnerConfig.put("name", "Example AWS Service");
        standardAwsPartnerConfig.put("asn", "65333");
        standardAwsPartnerConfig.put("ownerAccount", "684021030471");
        standardAwsPartnerConfig.put("customerIpAddress", null);
        standardAwsPartnerConfig.put("amazonIpAddress", null);
        standardAwsPartnerConfig.put("amazonAsn", "65333");
        standardAwsPartnerConfig.put("authKey", null);
        standardAwsPartnerConfig.put("prefixes", null);
        standardAwsPartnerConfig.put("connectType", "AWS");
        endB.setPartnerConfig(standardAwsPartnerConfig);

        vxcDto.setaEnd(endA);
        vxcDto.setbEnd(endB);

        dto.getAssociatedVxcs().add(vxcDto);

        return dto;
    }


}