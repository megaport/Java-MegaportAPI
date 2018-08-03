package com.megaport.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutanixPortsDto {

	private Integer bandwidth;
	private String resource_type;
	private String service_key;
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

	public String getService_key() {
		return service_key;
	}

	public void setService_key(String service_key) {
		this.service_key = service_key;
	}

	public List<NutanixPortDto> getMegaports() {
		return megaports;
	}

	public void setMegaports(List<NutanixPortDto> megaports) {
		this.megaports = megaports;
	}

}
