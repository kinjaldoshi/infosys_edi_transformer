/**
 * 
 */
package com.stg.insurance.services;

import java.io.FileNotFoundException;
import java.util.List;

import org.json.JSONObject;

/**
 * @author abhinavkumar.gupta
 *
 */
public interface TemplateCustomizationServices {

	public List<String> getFileName(String format) throws FileNotFoundException;
	
	public void uploadCustomTemplateToS3 (JSONObject customTemplate) throws Exception;
	
	public String getTemplateByNameFromS3(String lineOfBusiness, String agencyName, String templateType, String templateName) throws Exception;
	
}
