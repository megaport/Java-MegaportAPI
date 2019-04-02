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
@JsonIgnoreProperties(ignoreUnknown = true)
public class VxcServiceDto implements Serializable {

    private String productUid;
    private String productName;
    private ProductType productType;
    private Integer rateLimit;
    private VxcDistanceBand distanceBand;
    private ProvisioningStatus provisioningStatus;
    private VxcEndDto aEnd;
    private VxcEndDto bEnd;
    private UsageAlgorithm usageAlgorithm;
    private String serviceKey;
    private Map<String, Object> partnerConfigs = new HashMap<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private VxcApproval vxcApproval;

    @JsonDeserialize(using = DateDeserializer.class)
    private Date createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date liveDate;

    @JsonDeserialize(using = DateDeserializer.class)
    private Date terminateDate;

    private String costCentre;
    private boolean locked;

    private Map<String,Object> resources = new HashMap<>();

    public VxcApproval getVxcApproval() {
        return vxcApproval;
    }

    public void setVxcApproval(VxcApproval vxcApproval) {
        this.vxcApproval = vxcApproval;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName( String productName ) {
        this.productName = productName;
    }

    public Date getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(Date liveDate) {
        this.liveDate = liveDate;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit( Integer rateLimit ) {
        this.rateLimit = rateLimit;
    }

    public VxcDistanceBand getDistanceBand() {
        return distanceBand;
    }

    public void setDistanceBand(VxcDistanceBand distanceBand) {
        this.distanceBand = distanceBand;
    }

    public ProvisioningStatus getProvisioningStatus() {
        return provisioningStatus;
    }

    public void setProvisioningStatus(ProvisioningStatus provisioningStatus) {
        this.provisioningStatus = provisioningStatus;
    }

    public UsageAlgorithm getUsageAlgorithm() {
        return usageAlgorithm;
    }

    public void setUsageAlgorithm(UsageAlgorithm usageAlgorithm) {
        this.usageAlgorithm = usageAlgorithm;
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

    public VxcEndDto getaEnd() {
        return aEnd;
    }

    public void setaEnd( VxcEndDto aEnd ) {
        this.aEnd = aEnd;
    }

    public VxcEndDto getbEnd() {
        return bEnd;
    }

    public void setbEnd( VxcEndDto bEnd ) {
        this.bEnd = bEnd;
    }

    public Map<String, Object> getPartnerConfigs() {
        return partnerConfigs;
    }

    public void setPartnerConfigs(Map<String, Object> partnerConfigs) {
        this.partnerConfigs = partnerConfigs;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public void setResources(Map<String, Object> resources) {
        this.resources = resources;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        VxcServiceDto that = ( VxcServiceDto ) o;

        if ( productUid != null ? !productUid.equals( that.productUid ) : that.productUid != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return productUid != null ? productUid.hashCode() : 0;
    }
}
