package com.okaru.dtomapper;

import java.util.Date;

import com.okaru.dtomapper.dto.AddressDTO;
import com.okaru.dtomapper.dto.BusinessAddressDTO;
import com.okaru.dtomapper.dto.BusinessAddressEmbeddedDTO;
import com.okaru.dtomapper.dto.BusinessDTO;
import com.okaru.dtomapper.dto.UserDTO;

public class TestDTOMapperHelper {
	
	public AddressDTO getTestAddressDTO(){
		AddressDTO dto = new AddressDTO();
		dto.setCity("Oakland");
		dto.setAddressState("California");
		dto.setZip(55555);
		return dto;
	}
	
	public BusinessDTO getTestBusinessDTO(){
		BusinessDTO dto = new BusinessDTO();
		dto.setName("Test Business Name");
		dto.setDateFounded(new Date());
		dto.setIndustryName("Test Mapping");
		return dto;
	}
	
	public BusinessAddressDTO getTestBusinessAddressDTO(){
		BusinessAddressDTO dto = new BusinessAddressDTO();
		dto.setCity("Oakland");
		dto.setAddressState("California");
		dto.setZip(55555);
		dto.setName("Test Business Name");
		dto.setDateFounded(new Date());
		dto.setIndustryName("Test Mapping");
		return dto;
	}
	
	public BusinessAddressEmbeddedDTO getBusinessAddressEmbeddedDTO(){
		BusinessAddressEmbeddedDTO dto = new BusinessAddressEmbeddedDTO();
		AddressDTO aDTO = getTestAddressDTO();
		BusinessDTO bDTO = getTestBusinessDTO();
		dto.setAddressDTO(aDTO);
		dto.setBusinessDTO(bDTO);
		return dto;
	}
	
	public UserDTO getTestUserDTO(){
		UserDTO dto = new UserDTO();
		dto.setUsername("username");
		dto.setPassword("password");
		dto.setBirthDate(new Date());
		dto.setLastName("lastname");
		dto.setFirstName("firstName");
		return dto;
	}
}
