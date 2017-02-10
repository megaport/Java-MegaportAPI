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
    private String companyUid;
    private String companyName;
    private ProvisioningStatus provisioningStatus;
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
    private Boolean vxcAutoApproval = false;
    private Boolean marketplaceVisibility = true;
    private Boolean virtual = false;
    private Integer term;
    private ProductType productType;

    private List<VxcServiceDto> associatedVxcs = new ArrayList<>();
    private List<IxServiceDto> associatedIxs  = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,Object> resources = new HashMap<>();

    private final List<Integer> validPortSpeeds = Arrays.asList(1000,10000,100000);
    private final List<Integer> validTerms = Arrays.asList(1,12,24,36,48,60);

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

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public ProvisioningStatus getProvisioningStatus() {
        return provisioningStatus;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setProvisioningStatus(ProvisioningStatus provisioningStatus ) {
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

    public Boolean getMarketplaceVisibility() {
        return marketplaceVisibility;
    }

    public void setMarketplaceVisibility(Boolean marketplaceVisibility) {
        this.marketplaceVisibility = marketplaceVisibility;
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

    public List<VxcServiceDto> getAssociatedVxcs() {
        return associatedVxcs;
    }

    public void setAssociatedVxcs( List<VxcServiceDto> associatedVxcs ) {
        if ( associatedVxcs != null ) this.associatedVxcs = associatedVxcs;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAssociatedIxs(List<IxServiceDto> associatedIxs) {
        this.associatedIxs = associatedIxs;
    }

    public void addAssociatedVxcs(Set<VxcServiceDto> associatedVxcs ) {
        if ( associatedVxcs != null ) this.associatedVxcs.addAll(associatedVxcs);
    }

    public List<IxServiceDto> getAssociatedIxs() {
        return associatedIxs;
    }

    public Boolean getVxcAutoApproval() {
        return vxcAutoApproval;
    }

    public void setVxcAutoApproval(Boolean vxcAutoApproval) {
        this.vxcAutoApproval = vxcAutoApproval;
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
