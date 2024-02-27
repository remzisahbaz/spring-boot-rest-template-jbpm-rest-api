package com.example.springbootresttemplatejbpmrestapi.controller;

import com.example.springbootresttemplatejbpmrestapi.model.BeyannameDTO;
import com.example.springbootresttemplatejbpmrestapi.model.GenericRequestBody;
import com.example.springbootresttemplatejbpmrestapi.model.JbpmProcessInstance;
import com.example.springbootresttemplatejbpmrestapi.model.WorkItemInstance;
import com.example.springbootresttemplatejbpmrestapi.service.DemoServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class Controller {

    private  final DemoServiceImp demoServiceImp;

    @GetMapping("process-state")
    private ResponseEntity<String> ProcessState(@RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "processInstanceId") String processInstanceId){

        return ResponseEntity.ok(demoServiceImp.getProcessInstanceState(containersId,processInstanceId));
    }
    @GetMapping("process-waiting")
    private ResponseEntity<List<WorkItemInstance>> AnyWaitingWorkItemsByProcessInstanceId(@RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "processInstanceId") String processInstanceId){

        return ResponseEntity.ok(demoServiceImp.getAnyWaitingWorkItemsByProcessInstanceId(containersId,processInstanceId));
    }
    @GetMapping("process-variables")
    private ResponseEntity<BeyannameDTO> ProcessInstanceVariables(@RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "processInstanceId") String processInstanceId, @RequestParam("variableName") String variableName) throws JSONException {

        return ResponseEntity.ok(demoServiceImp.getProcessInstanceVariables(containersId,processInstanceId,variableName));
    }
    @GetMapping("getcontainers")
    private Map<String, Object> containers(){

        return demoServiceImp.getContainers();
    }
    @GetMapping("process-information")
    private Map<String, Object> processInformation(
            @RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "processInstanceId") String processInstanceId){

        return demoServiceImp.getProcessInformation(containersId,processInstanceId);
    }
    @GetMapping("task-information")
    private Map<String, Object> TaskInformation(
            @RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "processInstanceId") String processInstanceId
            , @RequestParam(name = "workItemId") String workItemId){

        return demoServiceImp.getTaskInformation(containersId,processInstanceId,workItemId);
    }
    @PostMapping("start-new-process")
    private JbpmProcessInstance start(@RequestBody GenericRequestBody request
            , @RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "processId") String processId){

        return demoServiceImp.startProcess(request,containersId,processId);
    }
    @PutMapping("task-claim")
    private String taskClaim( @RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "taskItemId") String taskItemId){

        return demoServiceImp.taskStateStart(containersId,taskItemId);
    }
    @PutMapping("task-complete")
    private String taskComplete(@RequestBody GenericRequestBody request,
                                @RequestParam(name = "containersId") String containersId
            , @RequestParam(name = "taskItemId") String taskItemId) throws URISyntaxException {

        return demoServiceImp.taskComplete(request,containersId,taskItemId);
    }

}
