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
	public void apply(UserDTO someDto, Map<String, Object> objectMap) {
		String fullName = someDto.getFirstName() + " " + someDto.getLastName();
		User someUser = (User)objectMap.get("user");
		someUser.setFullName(fullName);
	}
}
