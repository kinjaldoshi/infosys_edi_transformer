/**
 * 
 */
package com.stg.insurance.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author kinjal.doshi
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class CommonProperties {

	@Value("${s3.bucket_name}")
	private String bucketName;

	@Value("${s3.prefix}")
	private String prefix;

	@Value("${s3.access_key}")
	private String accessKey;

	@Value("${s3.secret_key}")
	private String secretKey;

	@Value("${s3.region}")
	private String region;

	/**
	 * @return the bucketName
	 */
	public String getBucketName() {
		return bucketName;
	}

	/**
	 * @param bucketName
	 *            the bucketName to set
	 */
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @param accessKey
	 *            the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

}
