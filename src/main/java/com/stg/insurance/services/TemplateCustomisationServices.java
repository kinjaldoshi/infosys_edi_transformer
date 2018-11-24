/**
 * 
 */
package com.stg.insurance.services;

import java.io.IOException;

import com.stg.insurance.data.beans.genericmodel.Document;

/**
 * @author AdityaVerma05
 *
 */
public interface TemplateCustomisationServices {

	String enrichBulkObject(String template, String fileName, Document bulkObject) throws IOException;

}
