/**
 * 
 */
package com.stg.insurance.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.stg.insurance.properties.CommonProperties;

/**
 * @author kinjal.doshi
 *
 */
@Configuration
public class S3CloudConfig {
	
	@Autowired
	CommonProperties commonProperties;

	@Bean
	public AmazonS3 s3client() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(commonProperties.getAccessKey(), commonProperties.getSecretKey());

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.fromName(commonProperties.getRegion()))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();

		return s3Client;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
