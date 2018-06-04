package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by awells on 10/1/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    String legalName;
    String tradingName;
    Integer companyId;
    String companyUid;
    String description;
    String legalIdentifier;
    String altId;
    String phone;
    String www;
    boolean active;
    boolean serviceAgent;
    String email;
    Date createDate;
    private String fax;
    private String legalIdentifierType;


    private Map<String, Object> attributeTags = new HashMap<>();

    public CompanyDto(){}


    public Map<String, Object> getAttributeTags() {
        return attributeTags;
    }

    public void setAttributeTags(Map<String, Object> attributeTags) {
        this.attributeTags = attributeTags;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getName() {
        if (StringUtils.isBlank(tradingName)) {
            if (StringUtils.isBlank(legalName)) {
                return "no name recorded for id=[" + this.getCompanyId() + "]";
            } else {
                return legalName;
            }
        } else {
            return tradingName;
        }
    }


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLegalIdentifier() {
        return legalIdentifier;
    }

    public void setLegalIdentifier(String legalIdentifier) {
        this.legalIdentifier = legalIdentifier;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAltId() {
        return altId;
    }

    public void setAltId(String altId) {
        this.altId = altId;
    }

    public boolean isServiceAgent() {
        return serviceAgent;
    }

    public void setServiceAgent(boolean serviceAgent) {
        this.serviceAgent = serviceAgent;
    }


    @Override
    public String toString() {
        return "CompanyDto{" +
                "legalName='" + legalName + '\'' +
                ", tradingName='" + tradingName + '\'' +
                ", companyId=" + companyId +
                ", description='" + description + '\'' +
                ", legalIdentifier='" + legalIdentifier + '\'' +
                ", altId='" + altId + '\'' +
                ", phone='" + phone + '\'' +
                ", www='" + www + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                ", fax='" + fax + '\'' +
                '}';
    }


    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLegalIdentifierType() {
        return legalIdentifierType;
    }

    public void setLegalIdentifierType(String legalIdentifierType) {
        this.legalIdentifierType = legalIdentifierType;
    }

}
