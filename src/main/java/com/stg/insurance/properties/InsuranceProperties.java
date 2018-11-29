/**
 * 
 */
package com.stg.insurance.properties;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author kinjaldoshi
 *
 */

@Configuration
public class InsuranceProperties {
	
	@Value("${insurance.line-of-business}")
	private String lineOfBusinessList;
	
	@Value("${insurance.vehicleinsurance-agencies-list}")
	private String vehicleInsuranceAgenciesList;
	
	@Value("${insurance.healthinsurance-agencies-list}")
	private String heathInsuranceAgenciesList;
	
	@Value("${insurance.acord-templates-list}")
	private String acordTemplatesList;
	
	@Value("${insurance.other-templates-list}")
	private String otherTemplatesList;

	/**
	 * @return the lineOfBusinessList
	 */
	public List<String> getLineOfBusinessList() {
		return Arrays.asList(lineOfBusinessList.split(","));
	}

	/**
	 * @param lineOfBusinessList the lineOfBusinessList to set
	 */
	public void setLineOfBusinessList(String lineOfBusinessList) {
		this.lineOfBusinessList = lineOfBusinessList;
	}

	/**
	 * @return the vehicleInsuranceAgenciesList
	 */
	public List<String>  getVehicleInsuranceAgenciesList() {
		return Arrays.asList(vehicleInsuranceAgenciesList.split(","));
	}

	/**
	 * @param vehicleInsuranceAgenciesList the vehicleInsuranceAgenciesList to set
	 */
	public void setVehicleInsuranceAgenciesList(String vehicleInsuranceAgenciesList) {
		this.vehicleInsuranceAgenciesList = vehicleInsuranceAgenciesList;
	}

	/**
	 * @return the heathInsuranceAgenciesList
	 */
	public List<String>  getHeathInsuranceAgenciesList() {
		return Arrays.asList(heathInsuranceAgenciesList.split(","));
	}

	/**
	 * @param heathInsuranceAgenciesList the heathInsuranceAgenciesList to set
	 */
	public void setHeathInsuranceAgenciesList(String heathInsuranceAgenciesList) {
		this.heathInsuranceAgenciesList = heathInsuranceAgenciesList;
	}

	/**
	 * @return the acordTemplatesList
	 */
	public List<String>  getAcordTemplatesList() {
		return Arrays.asList(acordTemplatesList.split(","));
	}

	/**
	 * @param acordTemplatesList the acordTemplatesList to set
	 */
	public void setAcordTemplatesList(String acordTemplatesList) {
		this.acordTemplatesList = acordTemplatesList;
	}

	/**
	 * @return the otherTemplatesList
	 */
	public List<String>  getOtherTemplatesList() {
		return Arrays.asList(otherTemplatesList.split(","));
	}

	/**
	 * @param otherTemplatesList the otherTemplatesList to set
	 */
	public void setOtherTemplatesList(String otherTemplatesList) {
		this.otherTemplatesList = otherTemplatesList;
	}
}
