package com.okaru.dtomapper.rule;

import java.util.Map;

import com.okaru.dtomapper.dto.UserDTO;
import com.okaru.dtomapper.model.User;

/**
 * A sample conditional rule.
 * 
 * @author Peter
 *
 */
public class CreateUsernameRule extends Rule<UserDTO>{

	@Override
	public void apply(UserDTO someDto, Map<String, Object> objectMap) {
		if((someDto.getFirstName() != null && someDto.getFirstName().length() > 0)){
			User user = (User)objectMap.get("user");
			user.setUsername((someDto.getFirstName().charAt(0) + 
					someDto.getLastName()).toLowerCase());
		}
	}

	@Override
	public void reverse(UserDTO someDto, Map<String, Object> objectMap) {
		//No opertaion
	}

}
