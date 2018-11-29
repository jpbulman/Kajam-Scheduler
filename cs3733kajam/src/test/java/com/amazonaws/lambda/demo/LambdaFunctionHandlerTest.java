package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static final String SAMPLE_INPUT_STRING = "{\"foo\": \"bar\"}";
    private static final String EXPECTED_OUTPUT_STRING = "{\"headers\":{\"Access-Control-Allow-Origin\":\"*\",\"Access-Control-Allow-Methods\":\"GET,POST,OPTIONS\",\"Content-Type\":\"application\\/json\"},\"body\":\"{\\\"error\\\":\\\"Invalid input format Invalid start date Invalid end date\\\",\\\"httpCode\\\":400}\"}";    
    private static final String SAMPLE_INPUT_STRING2 = "{\n" + 
    		"    \"arg1\": \"name\",\n" + 
    		"    \"arg2\": \"3\",\n" + 
    		"    \"arg3\": \"5\",\n" + 
    		"    \"arg4\": \"2017\",\n" + 
    		"    \"arg5\": \"1\",\n" + 
    		"    \"arg6\": \"26\",\n" + 
    		"    \"arg7\": \"2018\",\n" + 
    		"    \"arg8\": \"6\",\n" + 
    		"    \"arg9\": \"15\",\n" + 
    		"    \"arg10\": \"15\"\n" + 
    		"}";
    private static final String EXPECTED_OUTPUT_STRING2 = "{\"headers\":{\"Access-Control-Allow-Origin\":\"*\",\"Access-Control-Allow-Methods\":\"GET,POST,OPTIONS\",\"Content-Type\":\"application\\/json\"},\"body\":\"{\\\"name\\\":\\\"name\\\",\\\"startTime\\\":{\\\"hour\\\":3,\\\"minute\\\":0,\\\"second\\\":0,\\\"nano\\\":0},\\\"endTime\\\":{\\\"hour\\\":5,\\\"minute\\\":0,\\\"second\\\":0,\\\"nano\\\":0},\\\"startDate\\\":{\\\"year\\\":2017,\\\"month\\\":1,\\\"day\\\":26},\\\"endDate\\\":{\\\"year\\\":2018,\\\"month\\\":6,\\\"day\\\":15},\\\"meetingDuration\\\":15,\\\"secretCode\\\":90661,\\\"id\\\":\\\"f24508ed-b053-41cb-bc90-66c2b7d0c7cd\\\",\\\"httpCode\\\":200}\"}";    
    
    Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
    
    // Test create schedule with completely invalid inputs
    @Test
    public void testLambdaFunctionHandler() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        InputStream input = new ByteArrayInputStream(SAMPLE_INPUT_STRING.getBytes());;
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("sample"));

        // TODO: validate output here if needed.
        String sampleOutputString = output.toString();
        Assert.assertEquals(EXPECTED_OUTPUT_STRING, sampleOutputString);
    }
    
    //Tests with valid inputs
    @Test
    public void testLambdaFunctionHandler2() throws IOException {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();

        InputStream input = new ByteArrayInputStream(SAMPLE_INPUT_STRING2.getBytes());;
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("sample"));

        // TODO: validate output here if needed.
        String sampleOutputString = output.toString();
        Assert.assertEquals(EXPECTED_OUTPUT_STRING2.contains("httpCode\\\":200"), sampleOutputString.contains("httpCode\\\":200"));
    }
}
