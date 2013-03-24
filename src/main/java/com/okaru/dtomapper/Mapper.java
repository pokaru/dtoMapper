package com.okaru.dtomapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.okaru.dtomapper.annotation.Rules;
import com.okaru.dtomapper.converter.ConverterFactory;
import com.okaru.dtomapper.exception.MapperException;
import com.okaru.dtomapper.exception.RuleException;
import com.okaru.dtomapper.rule.DefaultRuleFactory;
import com.okaru.dtomapper.rule.Rule;
import com.okaru.dtomapper.rule.RuleFactory;

/**
 * Maps values from dto objects to other objects and vice versa.  Mappings are
 * specified within the MappedField and MappedObject annotations within the
 * dto.
 * 
 * @author pokaru
 */
public class Mapper{
	private RuleFactory ruleFactory;
	private ConverterFactory converterFactory;
	private MapperUtils mapperUtils = new MapperUtils();
	
	/**
	 * Maps field values from <code>someDto</code> to fields of objects in the
	 * <code>objectMap</code>.
	 * 
	 * @param someDto
	 * @param objectMap
	 */
	public void fromDto(Object someDto, ObjectMap objectMap) throws MapperException{
		beginMapping(someDto, objectMap, true);
		applyRules(someDto, objectMap.getObjectMap(), false);
	}

	/**
	 * Maps field values from fields of the objects in <code>objectMap</code> to 
	 * fields of <code>someDto</code>.
	 * 
	 * @param someDto
	 * @param objectMap
	 */
	public void toDto(Object someDto, ObjectMap objectMap) throws MapperException{
		beginMapping(someDto, objectMap, false);
		applyRules(someDto, objectMap.getObjectMap(), true);
	}
	
	private void beginMapping(Object someDto, ObjectMap objectMap, boolean toObject) throws MapperException{
		if(someDto == null){
			throw new MapperException("The DTO cannot be null.");
		}
		
		if(objectMap == null){
			throw new MapperException("The object map cannot be null.");
		}
		
		String clmd = mapperUtils.getClassLevelMappingDestination(someDto);
		
		Field[] fields = mapperUtils.getFields(someDto.getClass());
		for(Field field : fields){
			if(!mapperUtils.isIgnored(field)){
				if(mapperUtils.isEmbeddedDto(field)){
					Object embeddedDto;
					try {
						field.setAccessible(true);
						embeddedDto = field.get(someDto);
						if(embeddedDto == null){
							field.set(someDto, field.getType().newInstance());
							embeddedDto = field.get(someDto);
						}
						beginMapping(embeddedDto, objectMap, toObject);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
	                    e.printStackTrace();
                    }
				}else{
					String flmd = mapperUtils.getFieldLevelMappingDestination(field);
					
					boolean transferNulls = mapperUtils.getTransferNulls(field);
		
					String destination = null;
					if(clmd == null && flmd == null){
						throw new MapperException("No object to map " +
								"field, " + field.getName() + ", to.  Check to" +
							    " make sure you reference an object in either " +
							    "@MappedObject on the DTO class or @MappedField" +
							    " on the \""+field.getName()+"\" within " + 
							    someDto.getClass().getName());
					}
					destination = (flmd == null)? clmd:flmd;
					transfer(field, someDto, destination, objectMap, toObject, transferNulls);
				}
			}
		}
	}

	/**
	 * Maps <code>field</code> of <code>someDto</code> to the object mapped in
	 * <code>objectMap</code> with the key <code>destination</code>'s classname.
	 * 
	 * @param field
	 * @param someDto
	 * @param destination
	 * @param objectMap
	 */
	private void transfer(Field field, Object someDto,
			String destination, ObjectMap objectMap, boolean toObject,
			boolean transferNulls) throws MapperException{
		String fieldName = mapperUtils.getDestinationFieldName(field);
		Object object = objectMap.get(destination);
		if(object != null){
			if(mapperUtils.mapsToField(field)){
				handleFieldMapping(field, someDto, fieldName, object, toObject, transferNulls);
			}else{
				handleSetterMapping(field, someDto, fieldName, object, toObject, transferNulls);
			}
		}else{
			throw new MapperException("No object with the key \"" 
					+ destination + "\" found in objectMap. Referenced by " + 
					someDto.getClass().getName() + "." + field.getName());
		}
	}
	
	private void handleFieldMapping(Field field, Object someDto,
			String fieldName, Object object, boolean toObject, 
			boolean transferNulls) throws MapperException{
		Field objectField = null;
		try {
			objectField = mapperUtils.getField(object.getClass(), fieldName);
			objectField.setAccessible(true);
			field.setAccessible(true);
			
			if(toObject){
				if(transferNulls || (field.get(someDto) != null)){
					objectField.set(object, mapperUtils.convertToObjectType(field, someDto));
				}
			}else{
				if(transferNulls || (objectField.get(object) != null)){
					field.set(someDto, mapperUtils.convertFromObjectType(field, objectField, object));
				}
			}
		} catch (NoSuchFieldException e) {
			throw new MapperException("Field, " + fieldName + ", does " +
					"not exist on object " + object.getClass().getName(), e);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw new MapperException("Cannot map " + 
					field.getName() + " of " + 
					someDto.getClass().getName() + " to " + 
					objectField.getName() + " of " + 
					object.getClass().getName() + " because there is a type mismatch.", e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void handleSetterMapping(Field field, Object someDto,
			String fieldName, Object object, boolean toObject, boolean transferNulls) throws MapperException{
		try {
			if(toObject){
				String setterMethodName = mapperUtils.getSetterMethodName(fieldName);
				field.setAccessible(true);
				Method method;
				try {
					method = mapperUtils.getMethod(object.getClass(), setterMethodName, field.getType());
					method.setAccessible(true);
					Object someObject = field.get(someDto);
					if(transferNulls || (someObject != null)){
						method.invoke(object, someObject);
					}
				} catch (NoSuchMethodException e) {
					throw new MapperException("No such method \""+setterMethodName+
							"("+field.getType().getName()+")\" " +
									"in object " + object.getClass().getName(), e);
				} catch (IllegalArgumentException e) {
					throw new MapperException(e);
				}
			}else{
				String getterMethodName = mapperUtils.getGetterMethodName(fieldName);
				Method method;
				try {
					method = mapperUtils.getMethod(object.getClass(), getterMethodName);
					method.setAccessible(true);
					field.setAccessible(true);
					if(transferNulls || (method.invoke(object) != null)){
						field.set(someDto, method.invoke(object));
					}
				} catch (NoSuchMethodException e) {
					throw new MapperException("No such method \""+getterMethodName+"()\" " +
									"in object " + object.getClass().getName(), e);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Applies all specified rules.
	 * 
	 * @param someDto
	 * @param objectMap
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private void applyRules(Object someDto, Map<String, Object> objectMap, boolean reverse){
		Rules rules = someDto.getClass().getAnnotation(Rules.class);
		if(rules != null){
			Class<? extends Rule>[] ruleClassList = rules.value();
			
			for(Class<? extends Rule> ruleClass : ruleClassList){
				Rule rule = getRuleFactory().getRule(ruleClass);
				try{
					if(!reverse){
						rule.apply(someDto, objectMap);
					}else{
						rule.reverse(someDto, objectMap);
					}
				} catch(Exception e){
					throw new RuleException("The rule \"" + 
							rule.getClass().getName() + "\" has errors.", e);
				}
			}
		}
	}
	
	public RuleFactory getRuleFactory(){
		if(ruleFactory == null){
			ruleFactory = new DefaultRuleFactory();
		}
		return ruleFactory;
	}
	
	public void setRuleFactory(RuleFactory factory){
		this.ruleFactory = factory;
	}
	
	public void setConverterFactory(ConverterFactory factory){
		converterFactory = factory;
		mapperUtils.setConverterFactory(converterFactory);
	}
}