package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.megaport.api.util.DateDeserializer;

import java.io.Serializable;
import java.util.*;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MegaportServiceDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productUid;
    private String productName;
    private String provisioningStatus;
    @JsonDeserialize(using = DateDeserializer.class)
    private Date createDate;
    private Integer portSpeed;
    @JsonDeserialize(using = DateDeserializer.class)
    private Date terminateDate;
    private String market;
    private Integer locationId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String costCentre;
    private Boolean vxcpermitted;

    private Set<VxcServiceDto> associatedVxcs = new HashSet<>();
    private Set<IxServiceDto> associatedIxs  = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,Object> resources = new HashMap<>();

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

    public String getProvisioningStatus() {
        return provisioningStatus;
    }

    public void setProvisioningStatus( String provisioningStatus ) {
        this.provisioningStatus = provisioningStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate( Date createDate ) {
        this.createDate = createDate;
    }

    public Integer getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed( Integer portSpeed ) {
        this.portSpeed = portSpeed;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate( Date terminateDate ) {
        this.terminateDate = terminateDate;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket( String market ) {
        this.market = market;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCostCentre() {
        return costCentre;
    }

    public void setCostCentre(String costCentre) {
        this.costCentre = costCentre;
    }

    public Boolean getVxcpermitted() {
        return vxcpermitted;
    }

    public void setVxcpermitted(Boolean vxcpermitted) {
        this.vxcpermitted = vxcpermitted;
    }

    public Set<VxcServiceDto> getAssociatedVxcs() {
        return associatedVxcs;
    }

    public void setAssociatedVxcs( Set<VxcServiceDto> associatedVxcs ) {
        if ( associatedVxcs != null ) this.associatedVxcs = associatedVxcs;
    }

    public void addAssociatedVxcs( Set<VxcServiceDto> associatedVxcs ) {
        if ( associatedVxcs != null ) this.associatedVxcs.addAll(associatedVxcs);
    }

    public Set<IxServiceDto> getAssociatedIxs() {
        return associatedIxs;
    }

    public void setAssociatedIxs( Set<IxServiceDto> associatedIxs ) {
        if ( associatedIxs != null ) this.associatedIxs = associatedIxs;
    }

    public Map<String, Object> getResources() {
        return resources;
    }

    public void setResources(Map<String, Object> resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        MegaportServiceDto that = ( MegaportServiceDto ) o;

        if ( productUid != null ? !productUid.equals( that.productUid ) : that.productUid != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return productUid != null ? productUid.hashCode() : 0;
    }
}
