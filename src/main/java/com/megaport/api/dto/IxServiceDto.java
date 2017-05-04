package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.megaport.api.util.DateDeserializer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class IxServiceDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productUid;
    private String productName;
    private Integer vlan;
    private Integer rateLimit;
    private String macAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long asn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String networkServiceType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date terminateDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date liveDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String costCentre;
    private UsageAlgorithm usageAlgorithm;
    private ProductType productType;
    private ProvisioningStatus provisioningStatus;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,Object> resources = new HashMap<>();

    public ProductType getProductType() {
        return productType;
    }

    public ProvisioningStatus getProvisioningStatus() {
        return provisioningStatus;
    }

    public void setProvisioningStatus(ProvisioningStatus provisioningStatus) {
        this.provisioningStatus = provisioningStatus;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Date getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(Date liveDate) {
        this.liveDate = liveDate;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Long getAsn() {
        return asn;
    }

    public void setAsn(Long asn) {
        this.asn = asn;
    }

    public String getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(String networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

    public String getCostCentre() {
        return costCentre;
    }

    public void setCostCentre(String costCentre) {
        this.costCentre = costCentre;
    }

    public UsageAlgorithm getUsageAlgorithm() {
        return usageAlgorithm;
    }

    public void setUsageAlgorithm(UsageAlgorithm usageAlgorithm) {
        this.usageAlgorithm = usageAlgorithm;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public void setResources(Map<String, Object> resources) {
        this.resources = resources;
    }

}
