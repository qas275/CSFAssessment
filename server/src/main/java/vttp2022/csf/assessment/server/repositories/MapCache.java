package vttp2022.csf.assessment.server.repositories;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Repository
public class MapCache {

	@Autowired
	AmazonS3 s3client;
	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public byte[] getMap(String key) throws IOException{
		// Implmementation in here
		GetObjectRequest getReq = new GetObjectRequest("qas275", "csf"+key);
		S3Object result = s3client.getObject(getReq);
		S3ObjectInputStream is = result.getObjectContent();
		return is.readAllBytes();
	}

	// You may add other methods to this class

}
