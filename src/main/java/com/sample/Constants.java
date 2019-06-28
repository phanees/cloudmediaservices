package com.sample;

import java.io.File;

public interface Constants {
	
	public final String IAM_ROLE = "arn:aws:iam::895099425585:role/mediaconvert-access-iam-role";
	public final String INPUT_S3_BUCKET_NAME = "codebuildaws";
	public final String INPUT_S3_BUCKET_NAME_FOR_MEDIACONVERT = "s3://"+INPUT_S3_BUCKET_NAME+"/";
	public final String INPUT_VIDEO_FILE = "testVideo.mp4";
	public final String OUTPUT_S3_BUCKET_NAME = "s3://codebuildaws/output/";
	public final String QUEUE = "arn:aws:mediaconvert:us-east-2:895099425585:queues/Default";

}
