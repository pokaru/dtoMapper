package com.okaru.dtomapper.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.okaru.dtomapper.Mapper;
import com.okaru.dtomapper.ObjectMap;
import com.okaru.dtomapper.dto.UserDTO;
import com.okaru.dtomapper.dto.WithListDTO;
import com.okaru.dtomapper.model.User;
import com.okaru.dtomapper.model.WithListModel;

public class MapUserListRule extends Rule<WithListDTO>{
	private Mapper mapper = new Mapper();

	@Override
	public void apply(WithListDTO someDto, Map<String, Object> objectMap) {
		List<UserDTO> userDtoList = someDto.getUserDtoList();
		List<User> userList = new ArrayList<User>();
		for(UserDTO dto : userDtoList){
			ObjectMap tempObjectMap = new ObjectMap();
			tempObjectMap.put("user", new User());
			mapper.fromDto(dto, tempObjectMap);
			userList.add((User)tempObjectMap.get("user"));
		}
		WithListModel model = (WithListModel)objectMap.get("withListModel");
		model.setUserList(userList);
	}

	@Override
	public void reverse(WithListDTO someDto, Map<String, Object> objectMap) {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<User> userList = ((WithListModel)objectMap.get("withListModel")).getUserList();
		for(User user : userList){
			ObjectMap tempObjectMap = new ObjectMap();
			tempObjectMap.put("user", user);
			UserDTO tempDto = new UserDTO();
			mapper.toDto(tempDto, tempObjectMap);
			userDtoList.add(tempDto);
		}
		someDto.setUserDtoList(userDtoList);
	}

}
