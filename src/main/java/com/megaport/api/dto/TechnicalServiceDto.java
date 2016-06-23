package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TechnicalServiceDto implements Serializable{

    private String serviceName = "";
    private String technicalServiceUid;
    private String companyName;
    private Integer companyId;
    private UsageAlgorithm billableUsageAlgorithm;
    private ProductType productType;
    private ProvisioningStatus provisioningStatus;
    private BillingStatus inAdvanceBillingStatus;
    private VxcDistanceBand vxcDistanceBand = null;
    private String intercapPath = null;
    private Boolean vxcPermitted = true;
    private Date createDate;
    private Date terminationDate;
    private Date contractStartDate;
    private Integer payerCompanyId;
    private Integer minimumSpeed;
    private Integer maximumSpeed;
    private List<Map<String,Object>> components = new ArrayList<>();
    private List<Map<String,Object>> attribtes = new ArrayList<>();
    private String aLocation;
    private String bLocation;
    private Integer aLocationId;
    private Integer bLocationId;

    public TechnicalServiceDto() {}

    public void updateMaximumSpeedFromComponents(){

        if (productType.equals(ProductType.VXC) || productType.equals(ProductType.CXC)){
            if (components.size() > 0) {
                Map<String,Object> component = components.get(0);
                if (component.get("type").equals("VXC")) {
                    List<Map<String, Object>> resources = (List<Map<String, Object>>) component.get("resources");
                    for (Map<String,Object> resource : resources){
                        if (resource.get("resource_type").equals("interface")){
                            Double portSpeed = (Double)resource.get("port_speed");
                            if (maximumSpeed == null){
                                maximumSpeed = portSpeed.intValue();
                                minimumSpeed = portSpeed.intValue();
                            } else {
                                if (portSpeed.intValue() > maximumSpeed){
                                    maximumSpeed = portSpeed.intValue();
                                }
                                if (portSpeed.intValue() < minimumSpeed){
                                    minimumSpeed = portSpeed.intValue();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTechnicalServiceUid() {
        return technicalServiceUid;
    }

    public void setTechnicalServiceUid(String technicalServiceUid) {
        this.technicalServiceUid = technicalServiceUid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public UsageAlgorithm getBillableUsageAlgorithm() {
        return billableUsageAlgorithm;
    }

    public void setBillableUsageAlgorithm(UsageAlgorithm billableUsageAlgorithm) {
        this.billableUsageAlgorithm = billableUsageAlgorithm;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProvisioningStatus getProvisioningStatus() {
        return provisioningStatus;
    }

    public void setProvisioningStatus(ProvisioningStatus provisioningStatus) {
        this.provisioningStatus = provisioningStatus;
    }

    public BillingStatus getInAdvanceBillingStatus() {
        return inAdvanceBillingStatus;
    }

    public void setInAdvanceBillingStatus(BillingStatus inAdvanceBillingStatus) {
        this.inAdvanceBillingStatus = inAdvanceBillingStatus;
    }

    public VxcDistanceBand getVxcDistanceBand() {
        return vxcDistanceBand;
    }

    public void setVxcDistanceBand(VxcDistanceBand vxcDistanceBand) {
        this.vxcDistanceBand = vxcDistanceBand;
    }

    public String getIntercapPath() {
        return intercapPath;
    }

    public void setIntercapPath(String intercapPath) {
        this.intercapPath = intercapPath;
    }

    public Boolean getVxcPermitted() {
        return vxcPermitted;
    }

    public void setVxcPermitted(Boolean vxcPermitted) {
        this.vxcPermitted = vxcPermitted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Integer getPayerCompanyId() {
        return payerCompanyId;
    }

    public void setPayerCompanyId(Integer payerCompanyId) {
        this.payerCompanyId = payerCompanyId;
    }

    public Integer getMinimumSpeed() {
        return minimumSpeed;
    }

    public void setMinimumSpeed(Integer minimumSpeed) {
        this.minimumSpeed = minimumSpeed;
    }

    public Integer getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(Integer maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public List<Map<String, Object>> getComponents() {
        return components;
    }

    public void setComponents(List<Map<String, Object>> components) {
        this.components = components;
    }

    public List<Map<String, Object>> getAttribtes() {
        return attribtes;
    }

    public void setAttribtes(List<Map<String, Object>> attribtes) {
        this.attribtes = attribtes;
    }

    public String getaLocation() {
        return aLocation;
    }

    public void setaLocation(String aLocation) {
        this.aLocation = aLocation;
    }

    public String getbLocation() {
        return bLocation;
    }

    public void setbLocation(String bLocation) {
        this.bLocation = bLocation;
    }

    public Integer getaLocationId() {
        return aLocationId;
    }

    public void setaLocationId(Integer aLocationId) {
        this.aLocationId = aLocationId;
    }

    public Integer getbLocationId() {
        return bLocationId;
    }

    public void setbLocationId(Integer bLocationId) {
        this.bLocationId = bLocationId;
    }
}
