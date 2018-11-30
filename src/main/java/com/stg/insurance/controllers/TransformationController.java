package com.stg.insurance.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stg.insurance.data.beans.genericmodel.Document;
import com.stg.insurance.services.TemplateCustomisationServices;

@RestController
@CrossOrigin
public class TransformationController {

	@Autowired
	private TemplateCustomisationServices templateCustomizationService;

	@PostMapping("/ediPlatform/transform/{template}")
	public ResponseEntity<byte[]> transformBulkDataToFixedTemplate(@RequestBody Document bulkDocument,
			@PathVariable(name = "template") String template,
			@RequestParam(name = "filename", required = true) String filename) throws IOException {
		byte[] fileBytes = templateCustomizationService.enrichBulkObject(template, filename, bulkDocument);
		HttpHeaders headers = new HttpHeaders();
		filename = filename.replace(".json", ".edi");
		headers.setContentDispositionFormData(filename, filename);
		
		return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.OK);
	}
}
