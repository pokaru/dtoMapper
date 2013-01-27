package com.okaru.dtomapper.model;

import java.util.Date;

public class Business {
	private String name;
	private Date founded;
	private Address address;
	private User owner;
	
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
}
