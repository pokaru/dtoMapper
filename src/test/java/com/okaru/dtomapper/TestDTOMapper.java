package com.okaru.dtomapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.okaru.dtomapper.dto.RegistrationDTO;
import com.okaru.dtomapper.model.Address;
import com.okaru.dtomapper.model.Business;
import com.okaru.dtomapper.model.User;

public class TestDTOMapper {

	@Test
	public void testMappingToAndFrom(){
		//Get your dto
		RegistrationDTO dto = getTestDTO();
		
		//Get your models
		Business business = new Business();
		User user = new User();
		Address address = new Address();
		business.setAddress(address);
		business.setOwner(user);

		//Place models into a map using an arbitrary key
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("business", business);
		modelMap.put("address", business.getAddress());
		modelMap.put("owner", business.getOwner());
		
		//Start the mapping
		Mapper.fromDto(dto, modelMap);

		Assert.assertEquals(dto.getBusinessName(), business.getName());
		Assert.assertEquals(dto.getStreet(), business.getAddress().getStreet());
		Assert.assertEquals(dto.getCity(), business.getAddress().getCity());
		Assert.assertEquals(dto.getState(), business.getAddress().getState());
		Assert.assertEquals(dto.getCountry(), business.getAddress().getCountry());
		Assert.assertEquals(dto.getZip(), business.getAddress().getZip());
		Assert.assertEquals(dto.getUsername(), business.getOwner().getUsername());
		Assert.assertEquals(dto.getPassword(), business.getOwner().getPassword());
		
		//now go the other way
		RegistrationDTO newDto = new RegistrationDTO();
		Mapper.toDto(newDto, modelMap);

		Assert.assertEquals(dto.getBusinessName(), newDto.getBusinessName());
		Assert.assertEquals(dto.getStreet(), newDto.getStreet());
		Assert.assertEquals(dto.getCity(), newDto.getCity());
		Assert.assertEquals(dto.getState(), newDto.getState());
		Assert.assertEquals(dto.getCountry(), newDto.getCountry());
		Assert.assertEquals(dto.getZip(), newDto.getZip());
		Assert.assertEquals(dto.getUsername(), newDto.getUsername());
		Assert.assertEquals(dto.getPassword(), newDto.getPassword());
	}

	@Test
	public void testIgnoreAnnotation(){
		//Get your dto
		RegistrationDTO dto = getTestDTO();
		dto.setRegistrationTime(new Date());
		
		//Get your models
		Business business = new Business();
		User user = new User();
		Address address = new Address();
		business.setAddress(address);
		business.setOwner(user);

		//Place models into a map
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("business", business);
		modelMap.put("address", business.getAddress());
		modelMap.put("owner", business.getOwner());
		
		Mapper.fromDto(dto, modelMap);
		
		//verify registration date isn't mapped to business founded date
		Assert.assertNull(business.getFounded());
	}
	
	private RegistrationDTO getTestDTO(){
		RegistrationDTO dto = new RegistrationDTO();
		//Business info
		dto.setBusinessName("Some Business");
		
		//Address info
		dto.setCity("Some City");
		dto.setCountry("Some Country");
		dto.setState("Some State");
		dto.setStreet("Some Street");
		dto.setZip(55555);
		
		//User info
		dto.setUsername("username");
		dto.setPassword("password");
		return dto;
	}
}
