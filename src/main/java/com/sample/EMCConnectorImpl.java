package com.sample;


import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;

public class EMCConnectorImpl {
	
	public MediaConvertClient connect() {
		
		/*
		 * AwsSessionCredentials awsCreds = AwsSessionCredentials.create(
		 * "access_key_id", "secret_key_id", "session_token");
		 */

		MediaConvertClient mcClient = MediaConvertClient.builder()
				//.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.region(Region.US_EAST_1)
				.build();
		
		return mcClient;
				
	}

}
