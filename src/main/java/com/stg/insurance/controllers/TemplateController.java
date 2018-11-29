package com.stg.insurance.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stg.insurance.properties.InsuranceProperties;

@RestController
@CrossOrigin
public class TemplateController {
	
	@Autowired
	private InsuranceProperties insuranceProperties;
	
	@GetMapping ("/ediPlatform/getAllTemplates")
	public List<String> getAllAgencies(@RequestParam ("agencyName") String agencyName){
		List<String> templates = new ArrayList<>();
		if (agencyName != null && !agencyName.isEmpty()) {
			if (agencyName.equals("Acord")) {
				templates.addAll(insuranceProperties.getAcordTemplatesList());
			}else {
				templates.addAll(insuranceProperties.getOtherTemplatesList());
			}
		}
		
		return templates;
	}

}
