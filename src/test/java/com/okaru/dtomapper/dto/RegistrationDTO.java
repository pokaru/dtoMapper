package com.okaru.dtomapper.dto;

import java.util.Date;

import com.okaru.dtomapper.annotation.Ignore;
import com.okaru.dtomapper.annotation.MappedField;
import com.okaru.dtomapper.annotation.MappedObject;

@MappedObject(key = "address")
public class RegistrationDTO {
	/*
	 * User fields
	 */
	@MappedField(mappedObjectKey="owner")
	private String username;
	@MappedField(mappedObjectKey="owner")
	private String password;
	
	/*
	 * Address fields
	 */
	private String street;
	private String city;
	private String state;
	private Integer zip;
	private String country;
	
	/*
	 * Business fields
	 */
	@MappedField(mappedObjectKey="business", field="name")
	private String businessName;
	
	/*
	 * Ignored fields
	 */
	@Ignore
	@MappedField(mappedObjectKey="business", field="founded")
	private Date registrationTime;
	
	public Date getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getZip() {
		return zip;
	}
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
