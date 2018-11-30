package com.stg.insurance.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stg.insurance.constants.Constants;
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

	@Value("${transmission.url:https://insurance-encrypt-decrypt-app.cfapps.io/security/encrypt}")
	private String transmissionUrl;
	
	@Value("${validation.url:https://insurance-validation-service.cfapps.io/ediPlatform/validateData}")
	private String validationUrl;

	private static final String FIELD_SEPARATOR = " ";

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateCustomisationServicesImpl.class);

	public byte[] enrichBulkObject(String templateName, String fileName, Document bulkObject) throws IOException {
		Document enrichedDocument = null;
		InsuranceTemplate templateObject = getTemplateFromS3(templateName, fileName);
		enrichedDocument = enrichFromTemplate(bulkObject, templateObject);
		String edi = ediEncoder(enrichedDocument);
		File file = File.createTempFile(templateName + "-", "-Transformed");
		try(FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(edi.getBytes());
		} catch (IOException e) {
			LOGGER.error("Some IO Error Occurred");
			throw e;
		}
		MultiValueMap<String, Object> mvMap = new LinkedMultiValueMap<>();
		mvMap.add("file", new FileSystemResource(file));
		LOGGER.info("EDI generated. Proceed for Validation.");
		restTemplate.postForEntity(validationUrl, mvMap, String.class);
		LOGGER.info("Document successfully validated");
		new Thread() {
			@Override
			public void run() {
				try {
					HttpHeaders headers = new HttpHeaders();
					headers.add("fileName", file.getName());
					LOGGER.info("Transmitting data to agency");					
					restTemplate.exchange(transmissionUrl, HttpMethod.POST, new HttpEntity<>(mvMap, headers), MultiValueMap.class);
					LOGGER.info("Data Transmitted to the Agency");
				} catch (Exception e) {
					LOGGER.error("An error while transmitting data to agency- [stacktrace]", e);
				}
			}
		}.start();
		return edi.getBytes();
	}

	private String ediEncoder(Document enrichedDocument) {
		StringBuilder ediBuilder = new StringBuilder();
		int catIter = 1;
		for (Category category : enrichedDocument.getCategories()) {
			int iteration = 1;
			for (Record record : category.getRecords()) {
				StringBuilder recordBuilder = new StringBuilder();
				String recordEdi = String.join(FIELD_SEPARATOR, record.getFields().entrySet().stream().map(
						entry -> formatValue(entry.getKey(), entry.getValue().getValue(), entry.getValue().getLength()))
						.collect(Collectors.toList()));
				recordBuilder.append(category.getId()).append(getZeroPaddedString(recordEdi.length(), 3))
						.append(Constants.FORMAT_FLAG).append(category.getGroupVersion())
						.append(Constants.RESERVED_CHARACTER).append(category.getProcessLevel())
						.append(getZeroPaddedString(catIter, 4)).append(category.getParentGroupDesignator())
						.append(category.getParentProcessLevel());
				if (!StringUtils.isEmpty(category.getParentGroupDesignator()))
					recordBuilder.append(getZeroPaddedString(iteration, 4));

				recordBuilder.append(FIELD_SEPARATOR).append(recordEdi).append(FIELD_SEPARATOR);
				ediBuilder.append(recordBuilder).append("\n");
				iteration++;
			}
			catIter++;
		}
		LOGGER.info("Generated the EDI \n{}", ediBuilder.toString());
		return ediBuilder.toString();
	}

	private String getZeroPaddedString(int value, int length) {
		String l = Integer.toString(value);
		StringBuilder builder = new StringBuilder();
		prependPlaceholder(builder, "0", l, length);
		builder.append(l);
		return builder.toString();
	}

	private void prependPlaceholder(StringBuilder builder, String placeHolder, String baseValue, int desiredLength) {
		int count = desiredLength - baseValue.length();
		for (int i = 0; i < count; i++) {
			builder.append(placeHolder);
		}
	}

	private String formatValue(String key, String value, String length) {
		int l = Integer.parseInt(length);
		String formattedValue = null;
		StringBuilder builder = new StringBuilder();
		if (value.length() < l) {
			builder.append(value);
			for (int i = 0; i < l - value.length(); i++) {
				builder.append(FIELD_SEPARATOR);
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

				// Set up additional fields
				catToBeAdded.setGroupVersion(templateCategory.getGroupVersion());
				catToBeAdded.setProcessLevel(templateCategory.getProcessLevel());
				catToBeAdded.setParentGroupDesignator(templateCategory.getParentGroupDesignator());
				catToBeAdded.setParentProcessLevel(templateCategory.getParentProcessLevel());
				catToBeAdded.setRecords(new LinkedList<>());
				matchingGenericCategory.getRecords().forEach(blkRecrd -> {
					Record recordToBeAdded = new Record();
					recordToBeAdded.setFields(new LinkedHashMap<>());
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
		String absolutePath = new StringBuilder(commonProperties.getPrefix()).append("/").append(template).append("/").append(fileName).toString();
		
		LOGGER.info("Getting file from S3 object at : {}", absolutePath);
		String templateBody = s3client.getObjectAsString(commonProperties.getBucketName(), absolutePath);
		LOGGER.info("Located the S3 Object");
		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Attempting to create POJO for the template");
			templateObject = mapper.readValue(templateBody, InsuranceTemplate.class);
			LOGGER.info("Created the POJO for Template");
		} catch (IOException e) {
			LOGGER.error("An Error occured while parsing template object - [stacktrace]", e);
			throw e;
		}
		return templateObject;
	}

}
