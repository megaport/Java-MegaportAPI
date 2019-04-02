package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class ServiceModificationTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.STAGING, "api.test", "Abc123");
        assertTrue(session.isValid());

    }

    @Test
    public void testChangeServiceName() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        MegaportServiceDto configuredPort = null;
        for (MegaportServiceDto port : ports){
            if (port != null && port.getProvisioningStatus() != null && port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                configuredPort = port;
                break;
            }
        }

        if (configuredPort != null) {
            String productUid = configuredPort.getProductUid();
            session.modifyPort("New Name", productUid);
            MegaportServiceDto product = session.findServiceDetailMegaport(productUid);
            assertEquals("New Name", product.getProductName());
        }
    }

    @Test
    public void testChangeServiceVisibility() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        MegaportServiceDto configuredPort = null;
        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED) || port.getProvisioningStatus().equals(ProvisioningStatus.LIVE)){
                configuredPort = port;
                break;
            }
        }

        if (configuredPort != null) {

            Map<String,Object> fieldMap = new HashMap<>();

            fieldMap.put("name", "New Name");
            fieldMap.put("marketplaceVisibility", false);

            String productUid = configuredPort.getProductUid();
            session.modifyPort(fieldMap, productUid);
            MegaportServiceDto product = session.findServiceDetailMegaport(productUid);
            assertEquals("New Name", product.getProductName());
            assertEquals(false, product.getMarketplaceVisibility());
        }
    }

    @Test
    public void testChangeIxService() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        String productUid = null;
        for (MegaportServiceDto port : ports){
            if (port != null && port.getProvisioningStatus() != null && port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                if (port.getAssociatedIxs().size() > 0){
                    for (IxServiceDto ix : port.getAssociatedIxs()){
                        productUid = ix.getProductUid();
                        break;
                    }
                }
            }
        }

        if (productUid != null) {
            IxServiceModificationDto dto = new IxServiceModificationDto();
            dto.setProductUid(productUid);
            dto.setProductName("1234");
            dto.setAsn(1234L);
            dto.setVlan(1234);
            dto.setRateLimit(1234);
            session.modifyIx(dto);
            IxServiceDto product = session.findServiceDetailIx(productUid);
            assertEquals("New Name", product.getProductName());
        }
    }

    @Test
    public void testChangeVxcService() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        Set<MegaportServiceDto> liveOrConfigured = new HashSet<>();
        for (MegaportServiceDto port : ports){
            if (port != null && port.getProvisioningStatus() != null && port.getProvisioningStatus().equals(ProvisioningStatus.LIVE) || port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                liveOrConfigured.add(port);
            }
        }

        // look for a testing service that is not decommissioned
        String productUid = null;
        for (MegaportServiceDto port : liveOrConfigured){
            Integer id = new Random().nextInt(liveOrConfigured.size() -1);
            MegaportServiceDto random = ports.get(id);
            if (random.getAssociatedVxcs().size() > 0){
                if (random.getAssociatedVxcs().get(0).getProvisioningStatus() != ProvisioningStatus.DECOMMISSIONED) {
                    productUid = random.getAssociatedVxcs().get(0).getProductUid();
                    break;
                }
            }
        }


        if (productUid != null) {
            VxcServiceModificationDto dto = new VxcServiceModificationDto();
            dto.setProductUid(productUid);
            dto.setProductName("1234");
            dto.setRateLimit(1234);
            dto.setaEndVlan(1234);
            session.modifyVxcOrCxc(dto);
            VxcServiceDto product = session.findServiceDetailVxc(productUid);
            assertEquals("1234", product.getProductName());
            assertFalse(product.isLocked());
        }
    }

    @Test
    public void testChangeVxcServiceBad() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        String productUid = null;
        for (MegaportServiceDto port : ports){
            if (port != null && port.getProvisioningStatus() != null && port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                if (port.getAssociatedVxcs().size() > 0){
                    for (VxcServiceDto vxc : port.getAssociatedVxcs()){
                        productUid = vxc.getProductUid();
                    }
                }
            }
        }

        if (productUid != null) {
            VxcServiceModificationDto dto = new VxcServiceModificationDto();
            dto.setProductUid(productUid);
            dto.setProductName("1234");
            dto.setRateLimit(-1);
            dto.setaEndVlan(111111);

            try {
                session.modifyVxcOrCxc(dto);
                fail();
            } catch (Exception e){
                // expect failure with good message
                System.out.println(e);
            }
        }
    }

}