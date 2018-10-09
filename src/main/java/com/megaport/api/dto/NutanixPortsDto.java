package com.megaport.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutanixPortsDto {

	private Integer bandwidth;
	private String resource_type;
	private String serviceKey;

	private List<NutanixPortDto> megaports;

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getResource_type() {
		return resource_type;
	}

	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(String service_key) {
		this.serviceKey = service_key;
	}

	public List<NutanixPortDto> getMegaports() {
		return megaports;
	}

	public void setMegaports(List<NutanixPortDto> megaports) {
		this.megaports = megaports;
	}

	@Override
	public String toString() {
		return "NutanixPortsDto{" +
				"bandwidth=" + bandwidth +
				", resource_type='" + resource_type + '\'' +
				", serviceKey='" + serviceKey + '\'' +
				", megaports=" + megaports +
				'}';
	}
}
