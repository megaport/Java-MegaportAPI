package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class ServiceKeyTest extends SessionHelper {

    MegaportApiSession session;

    MegaportServiceDto livePort;

    @Before
    public void init() throws Exception{

        session = getSession();

        assertTrue(session.isValid());

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned

        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.LIVE)){
                livePort = port;
                break;
            }
        }


    }

    @Test
    public void testFindServiceKeys() throws Exception{

        List<ServiceKeyDto> keysByCompany = session.findServiceKeyByCompany(1153);
        assertTrue(keysByCompany.size() > 0);

        // find by Company
        String key = keysByCompany.get(0).getKey();
        String megaportUid = keysByCompany.get(0).getProductUid();

        // find by key
        ServiceKeyDto foundKey = session.findServiceKeyByKey(key);
        assertEquals(keysByCompany.get(0), foundKey);

        // find by service
        foundKey = session.findServiceKeyByPort(megaportUid).get(0);
        assertEquals(keysByCompany.get(0), foundKey);

    }

    @Test
    public void testValidateServiceKey() throws Exception{

        List<ServiceKeyDto> keysByCompany = session.findServiceKeyByCompany(1153);
        assertTrue(keysByCompany.size() > 0);

        // find by Company
        String key = keysByCompany.get(0).getKey();

        // validate
        Boolean validation = session.validateServiceKey(key);
        assertFalse(validation); // old expired key, so expect false

    }

    @Test
    public void createAndEditServiceKey() throws Exception{

        ServiceKeyDto key = new ServiceKeyDto();

        key.setVlan(1234);
        key.setDescription("Some description");
        key.setMaxSpeed(500);
        key.setPreApproved(true);
        key.setProductUid(livePort.getProductUid());
        key.setSingleUse(true);
        key.setValidFor(new TimePeriodDto(new Date(), null));
        key.setPromoCode(null);

        ServiceKeyDto savedKey = session.createServiceKey(key);

        assertEquals(key.getVlan(), savedKey.getVlan());
        assertEquals(key.getDescription(), savedKey.getDescription());
        assertEquals(key.getMaxSpeed(), savedKey.getMaxSpeed());
        assertEquals(key.getPreApproved(), savedKey.getPreApproved());
        assertEquals(key.getProductUid(), savedKey.getProductUid());
        assertEquals(key.getSingleUse(), savedKey.getSingleUse());
        assertEquals(key.getValidFor(), savedKey.getValidFor());

        savedKey.setVlan(2222);
        ServiceKeyDto editedKey = session.editServiceKey(savedKey);
        assertEquals(savedKey.getVlan(), editedKey.getVlan());

    }



}