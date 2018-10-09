package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class McrToAzureTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123", null);
        assertTrue(session.isValid());

    }

    @Test
    public void testOrderMcrToAzure() throws Exception{

        AzurePortsDto ports = session.findAzurePorts("197d927b-90bc-4b1b-bffd-fca17a7ec735");
        assertTrue(ports != null);
        System.out.println(">> Azure ports: " + ports);

        MegaportServiceDto goodVxc = createGoodVxc(ports);
        String order = session.placeOrder(Collections.singletonList(goodVxc));

        System.out.println(">> MCR to Azure order: " +order);

    }


    /**
     * {
     *   "productUid": "b17ed7a5-bc3e-42df-9d2e-044dbc324a0f",
     *   "associatedVxcs": [
     *     {
     *       "rateLimit": 500,
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
     *         "productUid": "078df7f1-be83-4462-8f6f-a6d01cc66c36",
     *         "vlan": 3901,
     *         "partnerConfig": {
     *           "complete": false,
     *           "bVlan": 3901,
     *           "connectType": "AZURE",
     *           "serviceKey": "197d927b-90bc-4b1b-bffd-fca17a7ec735",
     *           "maxRateLimit": 500,
     *           "peers": [
     *             {
     *               "type": "private"
     *             }
     *           ]
     *         }
     *       },
     *       "productType": "VXC",
     *       "connectType": "AZURE"
     *     }
     *   ],
     *   "associatedIxs": [
     *
     *   ]
     * }
     */
    private MegaportServiceDto createGoodVxc(AzurePortsDto portsDto){

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
        endB.setProductUid(portsDto.getMegaports().get(0).getProductUid());

        Map<String,Object> standardAzurePartnerConfig = new HashMap<>();
        standardAzurePartnerConfig.put("connectType", "AZURE");
        standardAzurePartnerConfig.put("serviceKey", portsDto.getService_key());
        standardAzurePartnerConfig.put("bVlan", portsDto.getVlan());
        standardAzurePartnerConfig.put("maxRateLimit", portsDto.getBandwidth());

        Map<String,Object> peer = new HashMap<>();
        peer.put("type", "private");
        standardAzurePartnerConfig.put("peers", Collections.singletonList(peer));

        endB.setPartnerConfig(standardAzurePartnerConfig);

        vxcDto.setaEnd(endA);
        vxcDto.setbEnd(endB);

        dto.getAssociatedVxcs().add(vxcDto);

        return dto;
    }


}