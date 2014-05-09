package edu.ycp.cs320.rts.server;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSON {
	private static final ObjectMapper theObjectMapper = new ObjectMapper(); 
	public static ObjectMapper getObjectMapper() {
		theObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		theObjectMapper.setSerializationInclusion(Include.NON_NULL);
		return theObjectMapper; 
	}
}
