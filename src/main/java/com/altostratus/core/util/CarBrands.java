package com.altostratus.core.util;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CarBrands {

	@Value("${cars.brand}")
	private String myField;

	private List<String> myValues;

	@PostConstruct
	public void init() {
		myValues = Arrays.asList(StringUtils
				.tokenizeToStringArray(myField, ","));
	}

	public String getMyField() {
		return myField;
	}

	public void setMyField(String myField) {
		this.myField = myField;
	}

	public List<String> getMyValues() {
		return myValues;
	}

	public void setMyValues(List<String> myValues) {
		this.myValues = myValues;
	}

}
