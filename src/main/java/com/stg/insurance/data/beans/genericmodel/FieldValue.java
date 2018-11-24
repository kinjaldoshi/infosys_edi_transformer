package com.stg.insurance.data.beans.genericmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Aditya Verma
 * 
 *         A Field Value which will have value and description
 *
 */
public class FieldValue {
	private String value;
	private String description;
	@JsonIgnore
	private String position;
	@JsonIgnore
	private String delimiter;
	@JsonIgnore
	private String length;
	
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public FieldValue() {
	}
	
	public FieldValue(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
