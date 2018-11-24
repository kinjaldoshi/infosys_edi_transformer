package com.stg.insurance.data.beans.template;

import java.util.List;

/**
 * @author AdityaVerma05
 *
 */
public class Category {
	private String name;
	private String categoryId;
	private List<Field> fields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
