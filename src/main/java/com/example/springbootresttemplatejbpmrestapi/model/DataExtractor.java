package com.example.springbootresttemplatejbpmrestapi.model;

import java.util.List;
import java.util.Map;

public class DataExtractor {

    public static List<Map<String, Object>> extractContainers(ResponseData responseData) {
        if (responseData != null && responseData.getResult() != null) {
            Map<String, List<Map<String, Object>>> kieContainers = responseData.getResult().get("kie-containers");
            if (kieContainers != null) {
                return kieContainers.get("kie-container");
            }
        }
        return null;
    }
}
