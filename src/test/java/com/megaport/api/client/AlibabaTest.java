package com.megaport.api.client;

import com.megaport.api.dto.AzurePortsDto;
import com.megaport.api.dto.Environment;
import com.megaport.api.dto.MegaportServiceDto;
import com.megaport.api.dto.VxcServiceDto;
import com.megaport.api.util.JsonConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class AlibabaTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123", null);
        assertTrue(session.isValid());

    }


    @Test
    public void testOrderAlibaba() throws Exception {
        String orderString = "\n" +
                "    {\n" +
                "      \"productUid\": \"9726d549-3a17-4bf7-99ab-18bab958243a\",\n" +
                "      \"associatedVxcs\": [\n" +
                "        {\n" +
                "          \"rateLimit\": 123,\n" +
                "          \"productName\": \"AAAA\",\n" +
                "          \"aEnd\": {\n" +
                "            \"productUid\": \"9726d549-3a17-4bf7-99ab-18bab958243a\",\n" +
                "            \"vlan\": 3333\n" +
                "          },\n" +
                "          \"bEnd\": {\n" +
                "            \"productUid\": \"f27cbea1-e12a-492f-9c1a-4b030d4733fa\",\n" +
                "            \"vlan\": null,\n" +
                "            \"partnerConfig\": {\n" +
                "              \"vbrOwnerId\": \"5234561292937849\",\n" +
                "              \"connectType\": \"ALIBABA\",\n" +
                "              \"complete\": true\n" +
                "            }\n" +
                "          },\n" +
                "          \"productType\": \"VXC\",\n" +
                "          \"connectType\": \"ALIBABA\",\n" +
                "          \"parentPortUid\": [\n" +
                "            \"9726d549-3a17-4bf7-99ab-18bab958243a\",\n" +
                "            null\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "      }\n" +
                "    }\n" +
                "  ";

        MegaportServiceDto serviceDto = JsonConverter.fromJson(orderString, MegaportServiceDto.class);
        List<MegaportServiceDto> order = new ArrayList<>();
        order.add(serviceDto);

        String orderResult = session.placeOrder(order);
    }

}