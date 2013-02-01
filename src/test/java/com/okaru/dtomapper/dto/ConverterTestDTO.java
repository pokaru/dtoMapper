package com.okaru.dtomapper.dto;

import com.okaru.dtomapper.annotation.Convert;
import com.okaru.dtomapper.annotation.MappedField;
import com.okaru.dtomapper.annotation.MappedObject;
import com.okaru.dtomapper.converter.StringToIntegerConverter;

@MappedObject(key="converterTestModel")
public class ConverterTestDTO {
	@Convert(converter=StringToIntegerConverter.class)
	@MappedField(field="integer1")
	private String string1;

	public String getString1() {
		return string1;
	}
	public void setString1(String string1) {
		this.string1 = string1;
	}
}
