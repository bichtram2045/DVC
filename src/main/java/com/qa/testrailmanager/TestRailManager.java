package com.qa.testrailmanager;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;

import org.json.simple.JSONObject;

import com.gurock.testrail.APIClient;

public class TestRailManager {
//	public static String TEST_RUN_ID = "13";
	private static final int PROJECT_ID = 2;
	
	public static String TEST_RAIL_USERNAME = System.getenv("TESTRAIL_USERNAME");
    public static String TEST_RAIL_PASSWORD = System.getenv("TESTRAIL_PASSWORD");
    
    public static String TEST_RAIL_ENGINE_URL = System.getenv("TESTRAIL_URL");

	public static String TEST_RUN_ID;
	
//	public static String TEST_RAIL_USERNAME = System.getenv("TESTRAIL_USERNAME");
//    public static String TEST_RAIL_PASSWORD = System.getenv("TESTRAIL_PASSWORD");
//    
//    public static String TEST_RAIL_ENGINE_URL = System.getenv("TESTRAIL_URL");
	

    
    public static int TEST_CASE_PASS_STATUS = 1;
    public static int TEST_CASE_FAIL_STATUS = 5;
    
    public static void createTestRun() {

        try {
            APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
            client.setUser(TEST_RAIL_USERNAME);
            client.setPassword(TEST_RAIL_PASSWORD);

            Map<String, Object> data = new HashMap<>();
            data.put("name", "Automation Run - " + new Date());
            data.put("include_all", true);

            JSONObject response = (JSONObject) client.sendPost(
                    "add_run/" + PROJECT_ID,
                    data
            );

            TEST_RUN_ID = response.get("id").toString();

            System.out.println("Created Test Run ID: " + TEST_RUN_ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    public static void addResultsForTestCase(String testCaseId, int status, String log) {
//        
//        String testRunID = TEST_RUN_ID;
//        APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
//        client.setUser(TEST_RAIL_USERNAME);
//        client.setPassword(TEST_RAIL_PASSWORD);
//        
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("status_id", status);
//        data.put("comment", "This test is executed via test automation code\n" + log);
//        
//        try {
//            client.sendPost("add_result_for_case/" + testRunID + "/" + testCaseId, data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    public static void addResultsForTestCase(String testCaseId, int status, String log, String screenshotPath) {

        String testRunID = TEST_RUN_ID;

        APIClient client = new APIClient(TEST_RAIL_ENGINE_URL);
        client.setUser(TEST_RAIL_USERNAME);
        client.setPassword(TEST_RAIL_PASSWORD);

        Map<String, Object> data = new HashMap<>();
        data.put("status_id", status);
        data.put("comment", "This test is executed via automation\n" + log);

        try {

            JSONObject response = (JSONObject) client.sendPost(
                    "add_result_for_case/" + testRunID + "/" + testCaseId,
                    data
            );

            // upload screenshot nếu có
            if (screenshotPath != null) {

                int resultId = Integer.parseInt(response.get("id").toString());

                client.sendPost(
                        "add_attachment_to_result/" + resultId,
                        screenshotPath
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
