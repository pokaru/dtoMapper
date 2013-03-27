package com.okaru.dtomapper;

import org.junit.Assert;
import org.junit.Test;

import com.okaru.dtomapper.dto.UserDTO;
import com.okaru.dtomapper.model.User;

public class TestDTOMapperUtils {
	
	MapperUtils mapperUtils = new MapperUtils();
	
	@Test
	public void testIsMappedObject(){
		boolean isDto = mapperUtils.isMappedObject(new User());
		Assert.assertFalse(isDto);
		
		isDto = mapperUtils.isMappedObject(new UserDTO());
		Assert.assertTrue(isDto);
	}
	
	@Test
	public void testGetMappedObjectKey(){
		UserDTO userDto = new UserDTO();
		String key = mapperUtils.getMappedObjectKey(userDto);
		Assert.assertEquals("user", key);
	}

}
