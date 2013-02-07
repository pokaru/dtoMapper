package com.okaru.dtomapper.model;

import java.util.List;

public class WithListModel {
	private List<User> userList;
	private List<String> someStrings;
	
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<String> getSomeStrings() {
		return someStrings;
	}
	public void setSomeStrings(List<String> someStrings) {
		this.someStrings = someStrings;
	}
}
