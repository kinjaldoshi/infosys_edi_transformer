package com.stg.insurance.data.beans.genericmodel;

import java.util.Map;

/**
 * @author Aditya Verma
 * 
 *         A record with fields
 *
 */
public class Record {

	private Map<String, FieldValue> fields;

	public Map<String, FieldValue> getFields() {
		return fields;
	}

	public void setFields(Map<String, FieldValue> fields) {
		this.fields = fields;
	}

}
