package com.okaru.dtomapper.dto;

import com.okaru.dtomapper.annotation.MappedField;
import com.okaru.dtomapper.annotation.MappedObject;

@MappedObject(key="address")
public class AddressDTO {
	private String city;
	
	@MappedField(field="state")
	private String addressState;
	
	private Integer zip;
	
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
