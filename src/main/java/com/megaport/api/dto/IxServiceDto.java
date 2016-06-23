package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
    private String macAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer asn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String networkServiceType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date terminateDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String costCentre;
    private UsageAlgorithm usageAlgorithm;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,Object> resources = new HashMap<>();

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


    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Integer getAsn() {
        return asn;
    }

    public void setAsn(Integer asn) {
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
