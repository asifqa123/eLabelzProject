package com.elabelz.qa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList; // Added for empty list return

public class JsonTestDataReader {
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, List<Map<String, String>>> testData;

    public JsonTestDataReader() {
        // Corrected resource path to match the actual location of testdata.json
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("com/elabelz/qa/testdata/testdata.json")) {
            if (inputStream == null) {
                // Adjusted error message to reflect correct path
                throw new RuntimeException("Cannot find com/elabelz/qa/testdata/testdata.json in classpath");
            }
            this.testData = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<Map<String, String>>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from JSON", e);
        }
    }

    public List<Map<String, String>> getTestData(String dataType) {
        return testData.getOrDefault(dataType, new ArrayList<Map<String, String>>()); // Return empty list if not found
    }
}
