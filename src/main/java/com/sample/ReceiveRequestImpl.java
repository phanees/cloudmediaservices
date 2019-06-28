package com.sample;

import com.sample.input.model.InputRequest;
import com.sample.util.Utils;

public class ReceiveRequestImpl {
	
	public Boolean receiveRequest(InputRequest inputRequest) {
		
		if(inputRequest != null) {
			if(Utils.isEmpty(inputRequest.getS3VideoURL()) 
					|| inputRequest.getEncodingOptions().isEmpty() || inputRequest.getSubTitleOptions().isEmpty()) {
				return false;
			}
		}
		
		return true;
	}

}
