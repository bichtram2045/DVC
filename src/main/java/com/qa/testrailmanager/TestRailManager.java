package com.qa.testrailmanager;

import java.util.HashMap;
import java.util.Map;

import com.gurock.testrail.APIClient;

public class TestRailManager {
	public static String TEST_RUN_ID = "13";
	
	public static String TEST_RAIL_USERNAME = System.getenv("TESTRAIL_USER");
    public static String TEST_RAIL_PASSWORD = System.getenv("TESTRAIL_PASSWORD");
    
    public static String TEST_RAIL_ENGINE_URL = System.getenv("TESTRAIL_URL");
    
    public static int TEST_CASE_PASS_STATUS = 1;
    public static int TEST_CASE_FAIL_STATUS = 5;
    
    public static void addResultsForTestCase(String testCaseId, int status, String error) {
        
        String testRunID = TEST_RUN_ID;
        APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
        client.setUser(TEST_RAIL_USERNAME);
        client.setPassword(TEST_RAIL_PASSWORD);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("status_id", status);
        data.put("comment", "This test is executed via test automation code\n" + error);
        
        try {
            client.sendPost("add_result_for_case/" + testRunID + "/" + testCaseId, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
