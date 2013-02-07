package com.okaru.dtomapper.dto;

import java.util.List;

import com.okaru.dtomapper.annotation.MappedField;
import com.okaru.dtomapper.annotation.MappedObject;
import com.okaru.dtomapper.annotation.Rules;
import com.okaru.dtomapper.rule.MapUserListRule;

@Rules({MapUserListRule.class})
@MappedObject(key="withListModel")
public class WithListDTO {
	@MappedField(field="userList")
	private List<UserDTO> userDtoList;
	
	public List<UserDTO> getUserDtoList() {
		return userDtoList;
	}
	public void setUserDtoList(List<UserDTO> userDtoList) {
		this.userDtoList = userDtoList;
	}
}
