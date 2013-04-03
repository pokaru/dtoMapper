package com.okaru.dtomapper.model;

import java.util.Date;

public class Business {
	private String name;
	private Date founded;
	private String industry;
	private Address address;
	private User owner;
	
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getFounded() {
		return founded;
	}
	public void setFounded(Date founded) {
		this.founded = founded;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public boolean isPublic(){
		return founded != null;
	}
	public void setPublic(Boolean value){
		System.out.println("This is a calculated value");
	}
}
