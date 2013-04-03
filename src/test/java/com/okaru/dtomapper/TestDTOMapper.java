package com.okaru.dtomapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.okaru.dtomapper.dto.AddressDTO;
import com.okaru.dtomapper.dto.BusinessAddressDTO;
import com.okaru.dtomapper.dto.BusinessAddressEmbeddedDTO;
import com.okaru.dtomapper.dto.BusinessDTO;
import com.okaru.dtomapper.dto.ConverterTestDTO;
import com.okaru.dtomapper.dto.CustomerDTO;
import com.okaru.dtomapper.dto.UserDTO;
import com.okaru.dtomapper.dto.WithListDTO;
import com.okaru.dtomapper.model.Address;
import com.okaru.dtomapper.model.Business;
import com.okaru.dtomapper.model.ConverterTestModel;
import com.okaru.dtomapper.model.Customer;
import com.okaru.dtomapper.model.User;
import com.okaru.dtomapper.model.WithListModel;

/**
 * A suite of test to test the dto <code>Mapper</code>
 * 
 * @author pokaru
 * 
 */
public class TestDTOMapper {
	private static Mapper mapper;
	private TestDTOMapperHelper testHelper = new TestDTOMapperHelper();
	
	@BeforeClass
	public static void before(){
		mapper = new Mapper();
	}

	/* Success Cases */

	@Test
	public void testClassLevelMappingFromDtoToMappedField() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		objectMap.put("address", address);

		// get a test dto
		AddressDTO someDto = testHelper.getTestAddressDTO();

		// perform mapping and validate
		mapper.fromDto(someDto, objectMap);
		Assert.assertEquals(someDto.getCity(), address.getCity());
	}

	@Test
	public void testFieldLevelMappingFromDtoToMappedField() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Business business = new Business();
		objectMap.put("business", business);

		// get a test dto
		BusinessDTO someDto = testHelper.getTestBusinessDTO();

		// perform mapping and validate
		mapper.fromDto(someDto, objectMap);
		Assert.assertEquals(someDto.getName(), business.getName());
	}

	@Test
	public void testClassLevelMappingToDtoFromMappedField() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		address.setCity("San Jose");
		objectMap.put("address", address);

		// get a new dto
		AddressDTO someDto = new AddressDTO();

		// perform mapping and validate
		mapper.toDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getCity());
		Assert.assertEquals(someDto.getCity(), address.getCity());
	}

	@Test
	public void testFieldLevelMappingToDtoFromMappedField() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Business business = new Business();
		business.setName("Okaru Corp.");
		objectMap.put("business", business);

		// get a new dto
		BusinessDTO someDto = new BusinessDTO();

		// perform mapping and validate
		mapper.toDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getName());
		Assert.assertEquals(someDto.getName(), business.getName());
	}

	@Test
	public void testClassLevelMappingFromDtoDifferentFieldName() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		objectMap.put("address", address);

		// get a test dto
		AddressDTO someDto = testHelper.getTestAddressDTO();

		// perform mapping and validate
		mapper.fromDto(someDto, objectMap);
		Assert.assertEquals(someDto.getAddressState(), address.getState());
	}

	@Test
	public void testFieldLevelMappingFromDtoToDifferentMappedFieldName() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Business business = new Business();
		objectMap.put("business", business);

		// get a test dto
		BusinessDTO someDto = testHelper.getTestBusinessDTO();

		// perform mapping and validate
		mapper.fromDto(someDto, objectMap);
		Assert.assertEquals(someDto.getDateFounded(), business.getFounded());
	}

	@Test
	public void testClassLevelMappingToDtoFromDifferentMappedFieldName() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		address.setState("Texas");
		objectMap.put("address", address);

		// get a test dto
		AddressDTO someDto = new AddressDTO();

		// perform mapping and validate
		mapper.toDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getAddressState());
		Assert.assertEquals(someDto.getAddressState(), address.getState());
	}

	@Test
	public void testFieldLevelMappingToDtoFromDifferentMappedFieldName() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Business business = new Business();
		business.setFounded(new Date());
		objectMap.put("business", business);

		// get a test dto
		BusinessDTO someDto = new BusinessDTO();

		// perform mapping and validate
		mapper.toDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getDateFounded());
		Assert.assertEquals(someDto.getDateFounded(), business.getFounded());
	}

	@Test
	public void testMappingToMultipleObjectsToMappedFields() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		Business business = new Business();
		objectMap.put("business", business);
		objectMap.put("address", address);

		// get a test dto
		BusinessAddressDTO someDto = testHelper.getTestBusinessAddressDTO();

		// perform mapping and validate
		mapper.fromDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getCity());
		Assert.assertNotNull(someDto.getZip());
		Assert.assertNotNull(someDto.getAddressState());
		
		Assert.assertNotNull(someDto.getName());
		Assert.assertNotNull(someDto.getDateFounded());
		Assert.assertNotNull(someDto.getIndustryName());
		
		Assert.assertEquals(someDto.getCity(), address.getCity());
		Assert.assertEquals(someDto.getZip(), address.getZip());
		Assert.assertEquals(someDto.getAddressState(), address.getState());
		Assert.assertEquals(someDto.getIndustryName(), business.getIndustry());
		Assert.assertEquals(someDto.getName(), business.getName());
		Assert.assertEquals(someDto.getDateFounded(), business.getFounded());
	}

	@Test
	public void testMappingFromMultipleObjectsToDto() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		address.setCity("Sacramento");
		address.setZip(12345);
		address.setState("CA");
		
		Business business = new Business();
		business.setName("Business Name");
		business.setFounded(new Date());
		business.setIndustry("Unit Testing");
		
		objectMap.put("business", business);
		objectMap.put("address", address);

		// get a new dto
		BusinessAddressDTO someDto = new BusinessAddressDTO();
				
		// perform mapping and validate
		mapper.toDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getCity());
		Assert.assertNotNull(someDto.getZip());
		Assert.assertNotNull(someDto.getAddressState());
		
		Assert.assertNotNull(someDto.getName());
		Assert.assertNotNull(someDto.getDateFounded());
		Assert.assertNotNull(someDto.getIndustryName());
		
		Assert.assertEquals(someDto.getCity(), address.getCity());
		Assert.assertEquals(someDto.getZip(), address.getZip());
		Assert.assertEquals(someDto.getAddressState(), address.getState());
		Assert.assertEquals(someDto.getIndustryName(), business.getIndustry());
		Assert.assertEquals(someDto.getName(), business.getName());
		Assert.assertEquals(someDto.getDateFounded(), business.getFounded());
	}

	//@Test
	public void testClassLevelMappingFromDtoToMappedSetter() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testFieldLevelMappingFromDtoToMappedSetter() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testClassLevelMappingToDtoFromMappedSetter() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testFieldLevelMappingToDtoFromMappedSetter() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testClassLevelMappingFromDtoDifferentSetterName() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testFieldLevelMappingFromDtoToDifferentMappedSetterName() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testClassLevelMappingToDtoFromDifferentMappedSetterName() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testFieldLevelMappingToDtoFromDifferentMappedSetterName() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testMappingToMultipleObjectsFromMappedSetters() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testMappingFromMultipleObjectsToMappedSetters() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testMappingToMultipleObjectsWithDifferentSetterNames() {
		// TODO
		Assert.fail();
	}

	//@Test
	public void testMappingFromMultipleObjectsWithDifferentSetterNames() {
		// TODO
		Assert.fail();
	}

	@Test
	public void testIgnoreField() {
		User user = new User();
		ObjectMap objectMap = new ObjectMap();
		objectMap.put("user", user);
		
		UserDTO someDto = testHelper.getTestUserDTO();
		mapper.fromDto(someDto, objectMap);

		Assert.assertNotNull(someDto.getUsername());
		Assert.assertNotNull(someDto.getPassword());
		Assert.assertNotNull(someDto.getFirstName());
		Assert.assertNotNull(someDto.getLastName());
		Assert.assertNotNull(someDto.getBirthDate());

		Assert.assertNotSame(someDto.getUsername(), user.getUsername());
		Assert.assertNotSame(someDto.getPassword(), user.getPassword());

		Assert.assertNull(user.getUsername());
		Assert.assertNull(user.getPassword());
		Assert.assertEquals(someDto.getFirstName(), user.getFirstName());
		Assert.assertEquals(someDto.getLastName(), user.getLastName());
		Assert.assertEquals(someDto.getBirthDate(), user.getBirthDate());
	}

	@Test
	public void testMappingFromEmbeddedDto() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		Business business = new Business();
		objectMap.put("business", business);
		objectMap.put("address", address);

		// get a test dto
		BusinessAddressEmbeddedDTO someDto = testHelper.getBusinessAddressEmbeddedDTO();

		// perform mapping and validate
		mapper.fromDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getAddressDTO().getCity());
		Assert.assertNotNull(someDto.getAddressDTO().getZip());
		Assert.assertNotNull(someDto.getAddressDTO().getAddressState());
		
		Assert.assertNotNull(someDto.getBusinessDTO().getName());
		Assert.assertNotNull(someDto.getBusinessDTO().getDateFounded());
		Assert.assertNotNull(someDto.getBusinessDTO().getIndustryName());
		
		Assert.assertEquals(someDto.getAddressDTO().getCity(), address.getCity());
		Assert.assertEquals(someDto.getAddressDTO().getZip(), address.getZip());
		Assert.assertEquals(someDto.getAddressDTO().getAddressState(), address.getState());
		Assert.assertEquals(someDto.getBusinessDTO().getIndustryName(), business.getIndustry());
		Assert.assertEquals(someDto.getBusinessDTO().getName(), business.getName());
		Assert.assertEquals(someDto.getBusinessDTO().getDateFounded(), business.getFounded());
	}

	@Test
	public void testMappingToEmbeddedDto() {
		// add object to the map
		ObjectMap objectMap = new ObjectMap();
		Address address = new Address();
		address.setCity("Sacramento");
		address.setZip(12345);
		address.setState("CA");
		
		Business business = new Business();
		business.setName("Business Name");
		business.setFounded(new Date());
		business.setIndustry("Unit Testing");
		
		objectMap.put("business", business);
		objectMap.put("address", address);

		// get a new dto
		BusinessAddressEmbeddedDTO someDto = new BusinessAddressEmbeddedDTO();
		someDto.setAddressDTO(new AddressDTO());
		someDto.setBusinessDTO(new BusinessDTO());
				
		// perform mapping and validate
		mapper.toDto(someDto, objectMap);
		Assert.assertNotNull(someDto.getAddressDTO().getCity());
		Assert.assertNotNull(someDto.getAddressDTO().getZip());
		Assert.assertNotNull(someDto.getAddressDTO().getAddressState());
		
		Assert.assertNotNull(someDto.getBusinessDTO().getName());
		Assert.assertNotNull(someDto.getBusinessDTO().getDateFounded());
		Assert.assertNotNull(someDto.getBusinessDTO().getIndustryName());
		
		Assert.assertEquals(someDto.getAddressDTO().getCity(), address.getCity());
		Assert.assertEquals(someDto.getAddressDTO().getZip(), address.getZip());
		Assert.assertEquals(someDto.getAddressDTO().getAddressState(), address.getState());
		Assert.assertEquals(someDto.getBusinessDTO().getIndustryName(), business.getIndustry());
		Assert.assertEquals(someDto.getBusinessDTO().getName(), business.getName());
		Assert.assertEquals(someDto.getBusinessDTO().getDateFounded(), business.getFounded());
	}
	
	@Test
	public void testMappingToClassWithParent(){
		Customer customer = new Customer();
		ObjectMap objectMap = new ObjectMap();
		objectMap.put("customer", customer);
		
		CustomerDTO dto = testHelper.getTestCustomerDTO();
		mapper.fromDto(dto, objectMap);

		Assert.assertNotNull(dto.getFirstName());
		Assert.assertNotNull(dto.getLastName());
		Assert.assertNotNull(dto.getUsername());
		Assert.assertNotNull(dto.getNickname());
		Assert.assertNotNull(dto.getPassword());
		Assert.assertNotNull(dto.getBirthDate());

		Assert.assertEquals(dto.getFirstName(), customer.getFirstName());
		Assert.assertEquals(dto.getLastName(), customer.getLastName());
		Assert.assertEquals(dto.getUsername(), customer.getUsername());
		Assert.assertEquals(dto.getNickname(), customer.getNickname());
		Assert.assertEquals(dto.getPassword(), customer.getPassword());
		Assert.assertEquals(dto.getBirthDate(), customer.getBirthDate());
	}
	
	@Test
	public void testTypeConversion(){
		ConverterTestModel model = new ConverterTestModel();
		ObjectMap objectMap = new ObjectMap();
		objectMap.put("converterTestModel", model);
		
		ConverterTestDTO dto = new ConverterTestDTO();
		String someString = "16";
		dto.setString1(someString);
		mapper.fromDto(dto, objectMap);
		
		Assert.assertEquals(Integer.valueOf(someString), model.getInteger1());
		
		ConverterTestDTO newDto = new ConverterTestDTO();
		mapper.toDto(newDto, objectMap);
		
		Assert.assertEquals(someString, newDto.getString1());
	}
	
	@Test
	public void testCustomRules(){
		User user = new User();
		ObjectMap objectMap = new ObjectMap();
		objectMap.put("user", user);
		
		UserDTO someDto = testHelper.getTestUserDTO();
		mapper.fromDto(someDto, objectMap);
		
		Assert.assertEquals(someDto.getFirstName() + " " + someDto.getLastName(),
				user.getFullName());
	}
	
	@Test
	public void testDoNotTransferNullsForFields(){
		String firstName = "AlreadySetFirstName";
		User user = new User();
		user.setFirstName(firstName);
		ObjectMap objectMap = new ObjectMap();
		objectMap.put("user", user);
		
		UserDTO someDto = testHelper.getTestUserDTO();
		someDto.setFirstName(null);
		mapper.fromDto(someDto, objectMap);
		
		Assert.assertEquals(firstName, user.getFirstName());
	}
	
	@Test
	public void testTransferNullsForFields(){
		String lastName = "AlreadySetLastName";
		User user = new User();
		user.setLastName(lastName);
		ObjectMap objectMap = new ObjectMap();
		objectMap.put("user", user);
		
		UserDTO someDto = testHelper.getTestUserDTO();
		someDto.setLastName(null);
		mapper.fromDto(someDto, objectMap);
		
		Assert.assertNull(user.getLastName());
	}
	
	//@Test
	public void testDoNotTransferNullsForSetters(){
		// TODO
		Assert.fail();
	}
	
	//@Test
	public void testTransferNullsForSetters(){
		// TODO
		Assert.fail();
	}
	
	/* Failure Cases */

	//@Test(expected=MapperException.class)
	public void testMapToFieldConflictingTypes() {
		// TODO
		Assert.fail();
	}

	//@Test(expected=MapperException.class)
	public void testMapToSetterConflictingTypes() {
		// TODO
		Assert.fail();
	}

	//@Test(expected=MapperException.class)
	public void testMapToFieldNoSuchField() {
		// TODO
		Assert.fail();
	}

	//@Test(expected=MapperException.class)
	public void testMapToSetterNoSuchSetter() {
		// TODO
		Assert.fail();
	}
	
	@Test
	public void testListMappingRule(){
		WithListDTO dto = new WithListDTO();
		
		//create and set the dto list
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		for(int i=0; i<10; i++){
			userDtoList.add(testHelper.getTestUserDTO());
		}
		dto.setUserDtoList(userDtoList);
		
		//get a new model and add to the map
		WithListModel model = new WithListModel();
		ObjectMap map = new ObjectMap();
		map.put("withListModel", model);
		
		//perform the mapping
		mapper.fromDto(dto, map);
		
		//verify the model has a populated list of users
		List<User> userList = model.getUserList();
		Assert.assertNotNull(userList);
		for(User user : userList){
			Assert.assertNotNull(user.getFirstName());
			Assert.assertNotNull(user.getLastName());
		}
		
		//verify reverse works for a new Dto
		WithListDTO newDto = new WithListDTO();
		mapper.toDto(newDto, map);
		
		System.out.println("new dto: \n");
		List<UserDTO> newUserDtoList = newDto.getUserDtoList();
		Assert.assertNotNull(newUserDtoList);
		for(UserDTO userDto : newUserDtoList){
			Assert.assertNotNull(userDto.getFirstName());
			Assert.assertNotNull(userDto.getLastName());
		}
	}
	
	@Test
	public void testBooleanMappingsToDto(){
		Address address = new Address();
		address.setDomestic(true);
		
		AddressDTO addressDto = new AddressDTO();
		mapper.map(address, addressDto, false);
		
		Assert.assertTrue(addressDto.isDomestic());
	}
	
	@Test
	public void testBooleanMappingsFromDto(){
		AddressDTO addressDto = new AddressDTO();
		addressDto.setDomestic(true);
		
		Address address = new Address();
		mapper.map(addressDto, address);
		
		Assert.assertTrue(address.isDomestic());
	}
	
	@Test
	public void testBooleanMethodMappingsToDto(){
		Business business = new Business();
		business.setFounded(new Date());
		
		BusinessDTO businessDto = new BusinessDTO();
		mapper.map(business, businessDto, false);
		
		Assert.assertTrue(businessDto.isPublic());
	}
}
