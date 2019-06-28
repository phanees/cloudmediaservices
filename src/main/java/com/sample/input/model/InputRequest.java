package com.sample.input.model;

import java.util.HashMap;
import java.util.List;

public class InputRequest {
	
	private String s3VideoURL;
	private List<String> encodingOptions;
	private HashMap<String, String> subTitleOptions;
	
	public InputRequest() {
		super();
	}

	public InputRequest(String s3VideoURL, List<String> encodingOptions, HashMap<String, String> subTitleOptions) {
		super();
		this.s3VideoURL = s3VideoURL;
		this.encodingOptions = encodingOptions;
		this.subTitleOptions = subTitleOptions;
	}

	public String getS3VideoURL() {
		return s3VideoURL;
	}

	public void setS3VideoURL(String s3VideoURL) {
		this.s3VideoURL = s3VideoURL;
	}

	public List<String> getEncodingOptions() {
		return encodingOptions;
	}

	public void setEncodingOptions(List<String> encodingOptions) {
		this.encodingOptions = encodingOptions;
	}

	public HashMap<String, String> getSubTitleOptions() {
		return subTitleOptions;
	}

	public void setSubTitleOptions(HashMap<String, String> subTitleOptions) {
		this.subTitleOptions = subTitleOptions;
	}		

}
