package com.example.springbootresttemplatejbpmrestapi.service;

import com.example.springbootresttemplatejbpmrestapi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DemoServiceImp implements DemoService {

    private final RestTemplate restTemplate;
    private final static String basicPath = "http://localhost:8080/kie-server/services/rest";

    @Override
    public Map<String, Object> getContainers() {
        ResponseData responseData = restTemplate.getForEntity(basicPath + "/server/containers", ResponseData.class).getBody();
        List<Map<String, Object>> containers = DataExtractor.extractContainers(responseData);

        Map<String, Object> data = new HashMap<>();
        if (containers != null) {
            for (Map<String, Object> container : containers) {
                data = container;
            }
        }
        return data;
    }

    @Override
    public JbpmProcessInstance startProcess(GenericRequestBody requestBody, String containersId, String processId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wbadmin", "wbadmin");
        HttpEntity<GenericRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<JbpmProcessInstance> responseEntity = restTemplate.exchange(
                basicPath + "/server/containers/" + containersId + "/processes/" + processId + "/instances",
                HttpMethod.POST,
                requestEntity,
                JbpmProcessInstance.class
        );

        /*body kısmında : oluşan yeni process instance id */
        return responseEntity.getBody();

    }
    @Override
    public String taskStateStart(String containersId, String taskItemId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wbadmin", "wbadmin");
        HttpEntity<GenericRequestBody> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                basicPath
                        + "/server/containers/"
                        + containersId
                        +"/tasks/"
                        +taskItemId
                        +"/states/started",
                HttpMethod.PUT,
                requestEntity,
                Map.class
        );
        HttpStatusCode body = HttpStatusCode.valueOf(responseEntity.getStatusCode().value());
        if (body.value()==200 || body.value()==201)
        {
            return "Claim success";
        }
        else
        /*body kısmında : oluşan yeni process instance id */
        return "No task found to start";

    }

    @Override
    public String taskComplete(GenericRequestBody requestBody, String containersId, String taskItemId) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wbadmin", "wbadmin");
        HttpEntity<GenericRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                basicPath
                        + "/server/containers/"
                        + containersId
                        +"/tasks/"
                        +taskItemId
                        +"/states/completed",
                HttpMethod.PUT,
                requestEntity,
                Map.class
        );
        HttpStatusCode body = HttpStatusCode.valueOf(responseEntity.getStatusCode().value());
        if (body.value()==200 || body.value()==201)
        {
            return "task completed";
        }
        else
        /*body kısmında : oluşan yeni process instance id */
        return "No task found";
    }

    @Override
    public String getProcessInstanceState(String containersId, String processInstanceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wbadmin", "wbadmin");
        HttpEntity<GenericRequestBody> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                basicPath + "/server/containers/" + containersId + "/processes/instances/" + processInstanceId,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        Map<String, Object> body = responseEntity.getBody();
        int stateNo = (int) body.get("process-instance-state");
        JbpmProcessInstanceStateEnum state = null;
        if (stateNo == 1) {
            state = JbpmProcessInstanceStateEnum.ACTIVE;
        } else if (stateNo == 2) {
            state = JbpmProcessInstanceStateEnum.COMPLETED;
        } else if (stateNo == 3) {
            state = JbpmProcessInstanceStateEnum.ABORTED;
        }
        return "Process state :" + state;
    }

    @Override
    public Map<String, Object> getProcessInformation(String containersId, String processInstanceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wbadmin", "wbadmin");
        HttpEntity<GenericRequestBody> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                basicPath + "/server/containers/"
                        + containersId + "/processes/instances/" + processInstanceId,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        Map<String, Object> body = responseEntity.getBody();

        return body;
    }

    @Override
    public BeyannameDTO getProcessInstanceVariables(String containersId, String processInstanceId, String variableName) throws JSONException {
        /*amaç:  process-isnatance-id ile giriş oluşan datanın son halinin alınması */

        ResponseEntity<Map> responseData = restTemplate.getForEntity(basicPath
                        + "/server/containers/"
                        + containersId
                        + "/processes/instances/"
                        + processInstanceId + "/variables/instances"
                , Map.class);

        Map<String, List<Map<String, Object>>> body = (Map<String, List<Map<String, Object>>>) responseData.getBody();
        if (body.isEmpty()) {
            return null;
        }
        String processVariableName = body.get("variable-instance").get(1).get("name").toString();
        String input = body.get("variable-instance").get(1).get("value").toString();


        BeyannameDTO beyannameDTO = createBeyannameDTOFromData(input);
        System.out.println(beyannameDTO);

        if (processVariableName.equals(variableName)) {

            return beyannameDTO;
        }
        else
        return null;
    }

    @Override
    public List<WorkItemInstance> getAnyWaitingWorkItemsByProcessInstanceId(String containersId, String processInstanceId) {
        /*amaç:  process-isnatance-id ile sorgulama yap oluşan task var mı varsa task bilgilerini göster */

        ResponseEntity<Map> responseData = restTemplate.getForEntity(basicPath
                        + "/server/containers/"
                        + containersId
                        + "/processes/instances/"
                        + processInstanceId + "/workitems"
                , Map.class);
        Map<String, List<Map<String, Object>>> body = (Map<String, List<Map<String, Object>>>) responseData.getBody();
        if (body.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> workItemInstanceList = body.get("work-item-instance");
        List<WorkItemInstance> workItemInstance = new ArrayList<>();

        workItemInstanceList.stream().map(
                w -> workItemInstance.add(WorkItemInstance.builder()
                        .workItemId(w.get("work-item-id").toString())
                        .workItemName(w.get("work-item-name").toString())
                        .workItemState(JbpmTaskStateEnum.getByIndex(Integer.parseInt(w.get("work-item-state").toString())).getDisplayName())
                        .processInstanceId(w.get("process-instance-id").toString())
                        .containerId(w.get("container-id").toString())
                        .workItemParameters((Map<String, Object>) w.get("work-item-params"))
                        .build())
        ).forEach(System.out::println);


        return workItemInstance;
    }

    @Override
    public Map<String, Object> getTaskInformation(String containersId, String processInstanceId, String workItemId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wbadmin", "wbadmin");
        HttpEntity<GenericRequestBody> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                basicPath
                        + "/server/containers/"
                        + containersId
                        + "/processes/instances/"
                        + processInstanceId + "/workitems/"
                        + workItemId,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        Map<String, Object> body = responseEntity.getBody();
        return body;
    }

    private static BeyannameDTO createBeyannameDTOFromData(String data) {
        BeyannameDTO beyannameDTO = new BeyannameDTO();

        // Veriyi parse etme
        String[] keyValuePairs = data.substring(data.indexOf("{") + 1, data.indexOf("}")).split(", ");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            // Setter metodları kullanarak değerleri ayarlama
            switch (key) {
                case "gtip":
                    beyannameDTO.setGtip(value.replace("'", ""));
                    break;
                case "durum":
                    beyannameDTO.setDurum(value.replace("'", ""));
                    break;
                case "netmiktar":
                    beyannameDTO.setNetmiktar(Double.parseDouble(value));
                    break;
                case "hata":
                    beyannameDTO.setHata(Boolean.parseBoolean(value));
                    break;
                // Diğer field'lar için gerekirse eklemeler yapılabilir.
            }
        }

        return beyannameDTO;
    }
}
