package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class McrToSfdcTest extends SessionHelper {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = getSession();
        assertTrue(session.isValid());

    }

    @Test
    public void testOrderMcrToSfdc() throws Exception{

        MegaportServiceDto goodVxc = createGoodVxc();
        String order = session.placeOrder(Collections.singletonList(goodVxc));

        System.out.println(order);

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
     *         }
     *       },
     *       "bEnd": {
     *         "productUid": "5a28d2ef-cfd6-4873-8e2f-be4bc5729a64",
     *         "vlan": null,
     *         "partnerConfig": {
     *           "asn": "65333",
     *           "password": null,
     *           "prefixes": null,
     *           "connectType": "SFDC"
     *         }
     *       },
     *       "productType": "VXC",
     *       "connectType": "SFDC"
     *     }
     *   ],
     *   "associatedIxs": []
     * }
     */
    private MegaportServiceDto createGoodVxc(){

        MegaportServiceDto dto = new MegaportServiceDto();

        // existing MCR service
        dto.setProductUid("3af1e873-d71d-4e55-932a-d3f3dfff7391");

        VxcServiceDto vxcDto = new VxcServiceDto();
        vxcDto.setProductType(ProductType.VXC);
        vxcDto.setProductName("My VXC");
        vxcDto.setRateLimit(100);
        vxcDto.setProvisioningStatus(ProvisioningStatus.DESIGN);


        VxcEndDto endA = new VxcEndDto();
        endA.setVlan(null);
        Map<String,Object> optimisedPartnerConfig = new HashMap<>();
        endA.setPartnerConfig(optimisedPartnerConfig);

        VxcEndDto endB = new VxcEndDto();
        endB.setVlan(null);
        endB.setProductUid("ef9547ef-35a8-430d-923f-a5a885a4d0df");
        Map<String,Object> standardAwsPartnerConfig = new HashMap<>();
        standardAwsPartnerConfig.put("asn", "65333");
        standardAwsPartnerConfig.put("password", null);
        standardAwsPartnerConfig.put("prefixes", null);
        standardAwsPartnerConfig.put("connectType", "SFDC");
        endB.setPartnerConfig(standardAwsPartnerConfig);

        vxcDto.setaEnd(endA);
        vxcDto.setbEnd(endB);

        dto.getAssociatedVxcs().add(vxcDto);

        return dto;
    }


}