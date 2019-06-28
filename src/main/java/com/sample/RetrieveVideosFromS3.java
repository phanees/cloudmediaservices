package com.sample;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

public class RetrieveVideosFromS3 {

	public boolean retrieveVideos() {
		Region region = Region.US_EAST_2;

		S3Client s3Client =  S3Client.builder()
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.region(region)
				.build();


		ListObjectsV2Request listReq = ListObjectsV2Request.builder()
				.bucket(Constants.INPUT_S3_BUCKET_NAME) .maxKeys(1) .build();

		ListObjectsV2Iterable listRes = s3Client.listObjectsV2Paginator(listReq);

		
		  listRes.contents().stream() .forEach(content -> System.out.println(" Key: " +
		  content.key() + " size = " + content.size()));
		 
		
		/*
		 * listRes.contents().stream() .forEach(content ->
		 * s3Client.getObject(GetObjectRequest.builder().bucket(Constants.
		 * INPUT_S3_BUCKET_NAME).key(content.key()).build(),
		 * ResponseTransformer.toFile(Paths.get("multiPartKey"))));
		 */
		return true;
	}

	public static void main(String[] args) {

		RetrieveVideosFromS3 s3 = new RetrieveVideosFromS3();
		boolean retrieved = s3.retrieveVideos();
		System.out.println(retrieved);

	}

}
