package com.stg.insurance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.insurance.properties.InsuranceProperties;

@RestController
@CrossOrigin
public class LoBController {
	
	@Autowired
	InsuranceProperties insuranceProperties;
	
	@GetMapping ("/ediPlatform/getAllLoBs")
	public List<String> getAllLoBs(){
		System.out.println("LOBs = " + insuranceProperties.getLineOfBusinessList());
		return insuranceProperties.getLineOfBusinessList();
	}
}
