package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutanixPortDto {

	private String productUid;
	private Integer vxc;
	private Integer locationId;
	private String name;
	private String description;
	private Integer portSpeed;
	private String state;
	private String country;

	public String getProductUid() {
		return productUid;
	}

	public void setProductUid(String productUid) {
		this.productUid = productUid;
	}

	public Integer getVxc() {
		return vxc;
	}

	public void setVxc(Integer vxc) {
		this.vxc = vxc;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPortSpeed() {
		return portSpeed;
	}

	public void setPortSpeed(Integer portSpeed) {
		this.portSpeed = portSpeed;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
