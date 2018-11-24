package com.stg.insurance.data.beans.template;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author AdityaVerma05
 *
 */
public class Field {
	@JsonProperty("AL3ShortDescription")
	private String al3ShortDescription;
	@JsonProperty("AL3Id")
	private String al3Id;
	@JsonProperty("FormCaption")
	private String formCaption;
	@JsonProperty("Position")
	private String position;
	@JsonProperty("Delimiter")
	private String delimiter;
	@JsonProperty("Length")
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

	public String getAl3ShortDescription() {
		return al3ShortDescription;
	}

	public void setAl3ShortDescription(String al3ShortDescription) {
		this.al3ShortDescription = al3ShortDescription;
	}

	public String getAl3Id() {
		return al3Id;
	}

	public void setAl3Id(String al3Id) {
		this.al3Id = al3Id;
	}

	public String getFormCaption() {
		return formCaption;
	}

	public void setFormCaption(String formCaption) {
		this.formCaption = formCaption;
	}
}
