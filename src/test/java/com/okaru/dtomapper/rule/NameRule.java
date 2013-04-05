package com.okaru.dtomapper.rule;

import java.util.Map;

import com.okaru.dtomapper.dto.UserDTO;
import com.okaru.dtomapper.model.User;

/**
 * A sample rule that maps two fields of a DTO to one field of some other
 * object.
 * 
 * @author pokaru
 *
 */
public class NameRule extends Rule<UserDTO>{

	@Override
	public void fromDto(UserDTO someDto, Map<String, Object> objectMap) {
		String fullName = someDto.getFirstName() + " " + someDto.getLastName();
		User someUser = (User)objectMap.get("user");
		someUser.setFullName(fullName);
	}

	@Override
	public void toDto(UserDTO someDto, Map<String, Object> objectMap) {
		User someUser = (User)objectMap.get("user");
		String[] fullNameSplit = someUser.getFullName().split(" ");
		someDto.setFirstName(fullNameSplit[0]);
		someDto.setLastName(fullNameSplit[1]);
	}
}
