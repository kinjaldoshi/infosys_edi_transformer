/**
 * 
 */
package com.stg.insurance.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.stg.insurance.properties.S3Properties;

/**
 * @author kinjal.doshi
 *
 */
@Configuration
public class S3CloudConfig {
	
	@Autowired
	S3Properties s3Properties;

	@Bean
	public AmazonS3 s3client() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.fromName(s3Properties.getRegion()))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();

		return s3Client;
	}
}
