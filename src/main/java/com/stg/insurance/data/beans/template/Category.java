package com.stg.insurance.data.beans.template;

import java.util.List;

/**
 * @author AdityaVerma05
 *
 */
public class Category {
	private String name;
	private String categoryId;
	private String groupVersion;
	private String processLevel;
	private String parentGroupDesignator;
	private String parentProcessLevel;
	private List<Field> fields;

	public String getGroupVersion() {
		return groupVersion;
	}

	public void setGroupVersion(String groupVersion) {
		this.groupVersion = groupVersion;
	}

	public String getProcessLevel() {
		return processLevel;
	}

	public void setProcessLevel(String processLevel) {
		this.processLevel = processLevel;
	}

	public String getParentGroupDesignator() {
		return parentGroupDesignator;
	}

	public void setParentGroupDesignator(String parentGroupDesignator) {
		this.parentGroupDesignator = parentGroupDesignator;
	}

	public String getParentProcessLevel() {
		return parentProcessLevel;
	}

	public void setParentProcessLevel(String parentProcessLevel) {
		this.parentProcessLevel = parentProcessLevel;
	}

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
