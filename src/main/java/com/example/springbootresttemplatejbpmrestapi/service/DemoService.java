package com.example.springbootresttemplatejbpmrestapi.service;

import com.example.springbootresttemplatejbpmrestapi.model.BeyannameDTO;
import com.example.springbootresttemplatejbpmrestapi.model.GenericRequestBody;
import com.example.springbootresttemplatejbpmrestapi.model.JbpmProcessInstance;
import com.example.springbootresttemplatejbpmrestapi.model.WorkItemInstance;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface DemoService {
    public Map<String, Object> getContainers();

    JbpmProcessInstance startProcess(GenericRequestBody requestBody, String containersId, String processId);

    String taskStateStart(String containersId, String taskItemId) throws URISyntaxException;
    String taskComplete(GenericRequestBody requestBody,String containersId, String taskItemId) throws URISyntaxException;

    String getProcessInstanceState(String containersId, String processInstanceId);
    Map<String, Object> getProcessInformation(String containersId, String processInstanceId);
    BeyannameDTO getProcessInstanceVariables(String containersId, String processInstanceId, String variableName) throws JSONException;

    List<WorkItemInstance> getAnyWaitingWorkItemsByProcessInstanceId(String containersId, String processInstanceId);

    Map<String, Object> getTaskInformation(String containersId, String processInstanceId, String workItemId);
}
