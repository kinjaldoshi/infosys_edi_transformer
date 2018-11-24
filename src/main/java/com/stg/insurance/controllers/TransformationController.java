package com.stg.insurance.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stg.insurance.data.beans.genericmodel.Document;
import com.stg.insurance.services.TemplateCustomisationServices;

@RestController
@RequestMapping("/ediPlatform")
@CrossOrigin
public class TransformationController {

	@Autowired
	private TemplateCustomisationServices templateCustomizationService;

	@PostMapping("/transform/{template}")
	public ResponseEntity<String> transformBulkDataToFixedTemplate(@RequestBody Document bulkDocument,
			@PathVariable(name = "template") String template,
			@RequestParam(name = "filename", required = true) String filename) throws IOException {
		return new ResponseEntity<>(templateCustomizationService.enrichBulkObject(template, filename, bulkDocument), HttpStatus.OK);
	}
}
