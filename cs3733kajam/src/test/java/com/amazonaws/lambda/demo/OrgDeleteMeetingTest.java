package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import db.MeetingDAO;
import model.Meeting;

public class OrgDeleteMeetingTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"headers\":{\"Access-Control-Allow-Origin\":\"*\",\"Access-Control-Allow-Methods\":\"GET,POST,OPTIONS\",\"Content-Type\":\"application\\/json\"},\"body\":\"{\\\"error\\\":\\\"Invalid input format\\\",\\\"httpCode\\\":400}\"}";    
    private static final String SAMPLE_INPUT_STRING3 = "{\n" + 
    		"    \"timeSlotID\": \"eea5770b-84ac-446f-bfdc-442f26fb2987\",\n" + 
    		"}";
    Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
    
    // Test create schedule with completely invalid inputs
    @Test
    public void testOrgDeleteMeetingHandler() throws IOException {
    	OrgDeleteMeetingHandler handler = new OrgDeleteMeetingHandler();

        InputStream input = new ByteArrayInputStream(SAMPLE_INPUT_STRING.getBytes());;
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("sample"));

        // TODO: validate output here if needed.
        String sampleOutputString = output.toString();
        Assert.assertEquals(EXPECTED_OUTPUT_STRING, sampleOutputString);
    }
    
    @Test
    public void testOrgDeleteMeetingHandler2() throws IOException {
    	MeetingDAO dao = new MeetingDAO();

		UUID testID = UUID.fromString("2c78a573-b5a7-46ad-814c-ff3d33808b9a");
		
		UUID testSlot = UUID.fromString("eea5770b-84ac-446f-bfdc-442f26fb2987");
		
		Meeting meeting = new Meeting(testID, testSlot, "name", 88888);
		try {
			dao.addMeeting(meeting);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
    	OrgDeleteMeetingHandler handler = new OrgDeleteMeetingHandler();
        InputStream input = new ByteArrayInputStream(SAMPLE_INPUT_STRING3.getBytes());;
        OutputStream output = new ByteArrayOutputStream();
        
        

        handler.handleRequest(input, output, createContext("sample"));
        
        // TODO: validate output here if needed.
        String sampleOutputString = output.toString();
        Assert.assertTrue(sampleOutputString.contains("httpCode\\\":200"));
    }
}
