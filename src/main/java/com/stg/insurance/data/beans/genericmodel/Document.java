package com.stg.insurance.data.beans.genericmodel;

import java.util.List;

/**
 * @author Aditya Verma
 * 
 *         A Document which will have a main category on top of the heirarchy
 *
 */
public class Document {

	private List<Category> categories;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	
}
