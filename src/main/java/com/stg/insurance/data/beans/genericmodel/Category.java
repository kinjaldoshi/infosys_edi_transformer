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
	private List<Record> records;
	private List<Category> subCategories;

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
