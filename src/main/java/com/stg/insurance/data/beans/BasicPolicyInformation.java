/**
 * 
 */
package com.stg.insurance.data.beans;

import org.springframework.data.annotation.Id;

/**
 * @author kinjaldoshi
 *
 */
public class BasicPolicyInformation {
	
	@Id
	private String policyNumber;
	private String companyCode;
	private String lobCode;
	private String effectiveDateDT6;
	private String expirationDateDY;
	private double currentTermAmount;
	private double netChangeAmount;
	private String billingMethodCode;
	private String producerSubCode;
	private String effectiveDateDT8;
	private String expirationDateDT8;
	
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getLobCode() {
		return lobCode;
	}
	public void setLobCode(String lobCode) {
		this.lobCode = lobCode;
	}
	public String getEffectiveDateDT6() {
		return effectiveDateDT6;
	}
	public void setEffectiveDateDT6(String effectiveDateDT6) {
		this.effectiveDateDT6 = effectiveDateDT6;
	}
	public String getExpirationDateDY() {
		return expirationDateDY;
	}
	public void setExpirationDateDY(String expirationDateDY) {
		this.expirationDateDY = expirationDateDY;
	}
	public double getCurrentTermAmount() {
		return currentTermAmount;
	}
	public void setCurrentTermAmount(double currentTermAmount) {
		this.currentTermAmount = currentTermAmount;
	}
	public double getNetChangeAmount() {
		return netChangeAmount;
	}
	public void setNetChangeAmount(double netChangeAmount) {
		this.netChangeAmount = netChangeAmount;
	}
	public String getBillingMethodCode() {
		return billingMethodCode;
	}
	public void setBillingMethodCode(String billingMethodCode) {
		this.billingMethodCode = billingMethodCode;
	}
	public String getProducerSubCode() {
		return producerSubCode;
	}
	public void setProducerSubCode(String producerSubCode) {
		this.producerSubCode = producerSubCode;
	}
	public String getEffectiveDateDT8() {
		return effectiveDateDT8;
	}
	public void setEffectiveDateDT8(String effectiveDateDT8) {
		this.effectiveDateDT8 = effectiveDateDT8;
	}
	public String getExpirationDateDT8() {
		return expirationDateDT8;
	}
	public void setExpirationDateDT8(String expirationDateDT8) {
		this.expirationDateDT8 = expirationDateDT8;
	}
	

	
	
	

}
