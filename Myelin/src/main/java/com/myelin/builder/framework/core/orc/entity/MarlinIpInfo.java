package com.myelin.builder.framework.core.orc.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MarlinIpInfo implements Serializable {
	private String ip;
	private String countryCode;
	private String countryName;
	private String cityCode;
	private String cityName;
	private String subdivisionName;
	private String subdivisionCode;
	private String postal;
	private Double latitude;
	private Double longitude;

	public MarlinIpInfo() {
		
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSubdivisionName() {
		return subdivisionName;
	}

	public void setSubdivisionName(String subdivisionName) {
		this.subdivisionName = subdivisionName;
	}

	public String getSubdivisionCode() {
		return subdivisionCode;
	}

	public void setSubdivisionCode(String subdivisionCode) {
		this.subdivisionCode = subdivisionCode;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "MarlinIpInfo [ip=" + ip + ", countryCode=" + countryCode + ", countryName=" + countryName
				+ ", cityCode=" + cityCode + ", cityName=" + cityName + ", subdivisionName=" + subdivisionName
				+ ", subdivisionCode=" + subdivisionCode + ", postal=" + postal + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

	
}
