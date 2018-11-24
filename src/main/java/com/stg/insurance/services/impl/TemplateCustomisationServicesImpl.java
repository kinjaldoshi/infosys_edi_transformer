package com.stg.insurance.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stg.insurance.data.beans.genericmodel.Category;
import com.stg.insurance.data.beans.genericmodel.Document;
import com.stg.insurance.data.beans.genericmodel.FieldValue;
import com.stg.insurance.data.beans.genericmodel.Record;
import com.stg.insurance.data.beans.template.InsuranceTemplate;
import com.stg.insurance.properties.CommonProperties;
import com.stg.insurance.services.TemplateCustomisationServices;

/**
 * @author AdityaVerma05
 *
 */
@Service
public class TemplateCustomisationServicesImpl implements TemplateCustomisationServices {

	@Autowired
	private AmazonS3 s3client;

	@Autowired
	private CommonProperties commonProperties;

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("transmission.url")
	private String transmissionUrl;

	private final String fieldSeparater = " ";

	private final static Logger LOGGER = LoggerFactory.getLogger(TemplateCustomisationServicesImpl.class);

	public String enrichBulkObject(String templateName, String fileName, Document bulkObject) throws IOException {
		Document enrichedDocument = null;
		InsuranceTemplate templateObject = getTemplateFromS3(templateName, fileName);
		enrichedDocument = enrichFromTemplate(bulkObject, templateObject);
		String edi = ediEncoder(enrichedDocument);
		new Thread() {
			@Override
			public void run() {
				try {
					restTemplate.postForEntity(transmissionUrl, edi, Object.class);
				} catch (Exception e) {
					LOGGER.error("An error occured");
				}
			}
		}.start();
		return edi;
	}

	private String ediEncoder(Document enrichedDocument) {
		// TODO Auto-generated method stub
		StringBuilder ediBuilder = new StringBuilder();
		for (Category category : enrichedDocument.getCategories()) {
			ediBuilder.append(category.getId()).append(fieldSeparater);
			for (Record record : category.getRecords()) {
				ediBuilder
						.append(String.join(fieldSeparater, record.getFields().entrySet().stream()
								.map(entry -> formatValue(entry.getKey(), entry.getValue().getValue(), entry.getValue().getLength())).collect(Collectors.toList())))
						.append(fieldSeparater);
			}
		}
		LOGGER.info("Generated the EDI \n{}", ediBuilder.toString());
		return ediBuilder.toString();
	}

	private String formatValue(String key, String value, String length) {
		int l = Integer.parseInt(length);
		String formattedValue = null;
		StringBuilder builder = new StringBuilder(key).append(fieldSeparater);
		if (value.length() < l) {
			builder.append(value);
			for (int i = 0; i < l - value.length(); i++) {
				builder.append(fieldSeparater);
			}
			formattedValue = builder.toString();
		} else {
			formattedValue = builder.append(value.substring(0, l)).toString();
		}
		return formattedValue;
	}

	private Document enrichFromTemplate(Document bulkObject, InsuranceTemplate templateObject) {
		LOGGER.info("Started enrichment");
		Document enrichedDocument = new Document();
		enrichedDocument.setCategories(new LinkedList<>());
		templateObject.getCategories().stream().forEach(templateCategory -> {
			Optional<Category> matchingGenericCategoryOpt = bulkObject.getCategories().stream()
					.filter(matchingCat -> matchingCat.getId().equals(templateCategory.getCategoryId())).findFirst();
			if (matchingGenericCategoryOpt.isPresent()) {
				LOGGER.info("Found a matching category to add");
				Category matchingGenericCategory = matchingGenericCategoryOpt.get();
				Category catToBeAdded = new Category();
				catToBeAdded.setId(matchingGenericCategory.getId());
				catToBeAdded.setRecords(new ArrayList<>());
				matchingGenericCategory.getRecords().forEach(blkRecrd -> {
					Record recordToBeAdded = new Record();
					recordToBeAdded.setFields(new HashMap<>());
					templateCategory.getFields().forEach(templateField -> {
						FieldValue value = blkRecrd.getFields().get(templateField.getAl3Id()) == null
								? new FieldValue(
										getDefaultDelimiter(templateField.getDelimiter(), templateField.getLength()),
										templateField.getAl3ShortDescription())
								: blkRecrd.getFields().get(templateField.getAl3Id());
						value.setPosition(templateField.getPosition());
						value.setLength(templateField.getLength());
						value.setDelimiter(templateField.getDelimiter());
						recordToBeAdded.getFields().put(templateField.getAl3Id(), value);
					});
					catToBeAdded.getRecords().add(recordToBeAdded);
				});
				enrichedDocument.getCategories().add(catToBeAdded);
			}
		});
		LOGGER.info("Finished enrichment");
		return enrichedDocument;
	}

	private String getDefaultDelimiter(String delimiter, String length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < Integer.parseInt(length); i++) {
			builder.append(delimiter);
		}
		return builder.toString();
	}

	private InsuranceTemplate getTemplateFromS3(String template, String fileName) throws IOException {
		InsuranceTemplate templateObject = null;
		String absolutePath = commonProperties.getPrefix().concat(template).concat("/").concat(fileName);
		LOGGER.info("Getting file from S3 object at : {}", absolutePath);
		String templateBody = s3client.getObjectAsString(commonProperties.getBucketName(), absolutePath);
		LOGGER.info("Located the S3 Object");
		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Attempting to create POJO for the template");
			templateObject = mapper.readValue(templateBody, InsuranceTemplate.class);
			LOGGER.info("Created the POJO for Template");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("An Error occured while parsing template object");
			throw e;
		}
		return templateObject;
	}

}
