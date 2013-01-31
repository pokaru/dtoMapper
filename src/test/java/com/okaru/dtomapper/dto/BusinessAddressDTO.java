package com.okaru.dtomapper.dto;

import java.util.Date;

import com.okaru.dtomapper.annotation.MappedField;

public class BusinessAddressDTO {
	@MappedField(mappedObjectKey="business")
	private String name;
	
	@MappedField(mappedObjectKey="business", field="founded")
	private Date dateFounded;
	
	@MappedField(mappedObjectKey="business", field="industry")
	private String industryName;
	
	@MappedField(mappedObjectKey="address")
	private String city;
	
	@MappedField(mappedObjectKey="address", field="state")
	private String addressState;

	@MappedField(mappedObjectKey="address")
	private Integer zip;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateFounded() {
		return dateFounded;
	}

	public void setDateFounded(Date dateFounded) {
		this.dateFounded = dateFounded;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

}
