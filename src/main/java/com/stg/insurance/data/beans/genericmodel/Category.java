package com.stg.insurance.data.beans.genericmodel;

import java.util.List;

/**
 * @author Aditya Verma
 * 
 *         A category which can have further sub categories and records
 *
 */
public class Category {
	private String id;
	private String groupVersion;
	private String processLevel;
	private String parentGroupDesignator;
	private String parentProcessLevel;
	private List<Record> records;
	private List<Category> subCategories;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}
}
