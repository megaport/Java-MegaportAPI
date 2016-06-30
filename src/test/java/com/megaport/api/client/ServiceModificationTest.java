package com.megaport.api.client;

import com.megaport.api.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class ServiceModificationTest {

    MegaportApiSession session;

    @Before
    public void init() throws Exception{

        session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
        assertTrue(session.isValid());

    }

    @Test
    public void testChangeServiceName() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        MegaportServiceDto configuredPort = null;
        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
                configuredPort = port;
                break;
            }
        }

        if (configuredPort != null) {
            String productUid = configuredPort.getProductUid();
            session.modifyPort("New Name", productUid);
            TechnicalServiceDto product = session.findServiceDetail(productUid);
            assertEquals("New Name", product.getServiceName());
        }
    }

    @Test
    public void testChangeIxService() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        String productUid = null;
        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
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
            dto.setAsn(1234);
            dto.setMacAddress("a0:00:00:00:00:00");
            dto.setVlan(1234);
            dto.setRateLimit(1234);
            session.modifyIx(dto);
            TechnicalServiceDto product = session.findServiceDetail(productUid);
            assertEquals("New Name", product.getServiceName());
        }
    }

    @Test
    public void testChangeVxcService() throws Exception{

        List<MegaportServiceDto> ports = session.findPorts();

        // look for a testing service that is not decommissioned
        String productUid = null;
        for (MegaportServiceDto port : ports){
            if (port.getProvisioningStatus().equals(ProvisioningStatus.CONFIGURED)){
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
            dto.setRateLimit(1234);
            dto.setaEndVlan(1234);
            dto.setbEndVlan(1234);
            session.modifyVxcOrCxc(dto);
            TechnicalServiceDto product = session.findServiceDetail(productUid);
            assertEquals("1234", product.getServiceName());
        }
    }

}