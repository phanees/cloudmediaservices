package com.sample;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.CreateJobRequest;
import software.amazon.awssdk.services.mediaconvert.model.FileGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.HlsGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.Input;
import software.amazon.awssdk.services.mediaconvert.model.JobSettings;
import software.amazon.awssdk.services.mediaconvert.model.Output;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroup;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroupSettings;
import software.amazon.awssdk.services.mediaconvert.model.OutputGroupType;
import software.amazon.awssdk.services.mediaconvert.model.VideoCodec;
import software.amazon.awssdk.services.mediaconvert.model.VideoCodecSettings;
import software.amazon.awssdk.services.mediaconvert.model.VideoDescription;

public class CreateMP4VideoImpl {
	
	final String IAM_ROLE = "role";
	final String S3_BUCKET_NAME = "s3://MEDIA_BUCKETS";
	final String INPUT_VIDEO_FILE = "samplevideo.mov";
	final String OUTPUT_VIDEO_FILE_PATH = S3_BUCKET_NAME+File.separator+"output"+File.separator;
	final String QUEUE = "arn:aws:mediaconvert:us-west-2:505474453218:queues/Default";
	
	public void createVideo(String format) {	
		
		OutputGroup outputGroup = null;
		
		HashMap<String, String> userMetaData = new HashMap<String, String>();
		userMetaData.put("customer", "WIPRO");
		userMetaData.put("created", Instant.now().toString());
		
		
		MediaConvertClient mcClient = MediaConvertClient.builder()
				//.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.region(Region.US_EAST_2)
				.build();
		
		Collection<Output> outputCollection = new ArrayList<Output>();
	
		Output outputMP4 = Output.builder()
				.nameModifier("MP4")
				.videoDescription(VideoDescription.builder()
						.width(1280)
						.height(720)
						.codecSettings(VideoCodecSettings.builder()
								.codec(VideoCodec.H_264)													
								.build())
						.build())
				.build();

		outputCollection.add(outputMP4);
		outputGroup = OutputGroup.builder()
								.name("MP4 VIDEO ENCODING")
								.outputs(outputCollection)						
								.outputGroupSettings(OutputGroupSettings.builder()
								.type(OutputGroupType.FILE_GROUP_SETTINGS)
								.fileGroupSettings(FileGroupSettings.builder()
										.destination(OUTPUT_VIDEO_FILE_PATH+format+"."+INPUT_VIDEO_FILE)							
										.build())
								.build())
						.build();		
		
		
		JobSettings jobSettings = JobSettings.builder()
			.inputs(Input.builder().fileInput(S3_BUCKET_NAME+File.separator+INPUT_VIDEO_FILE).build())
			.outputGroups(outputGroup)
			.build();		
		
		CreateJobRequest createJobRequest = CreateJobRequest.builder()
											.settings(jobSettings)
											.role(IAM_ROLE)
											.queue(QUEUE)
											.userMetadata(userMetaData)
											.build();
		
		mcClient.createJob(createJobRequest);
		

	}

}
