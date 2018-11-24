package com.stg.insurance.data.beans.template;

import java.util.List;

/**
 * @author AdityaVerma05
 *
 */
public class InsuranceTemplate {
	private List<Category> categories;
	private String templateName;
	private String templateTimestamp;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateTimestamp() {
		return templateTimestamp;
	}

	public void setTemplateTimestamp(String templateTimestamp) {
		this.templateTimestamp = templateTimestamp;
	}
}
