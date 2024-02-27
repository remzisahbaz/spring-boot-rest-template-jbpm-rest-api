package com.example.springbootresttemplatejbpmrestapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Label;
import lombok.*;
import org.springframework.boot.context.properties.bind.Name;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkItemInstance {

    @JsonProperty("Task Numarası")
    private String workItemId;
    @JsonProperty("Process Adı")
    private String containerId;
    @JsonProperty("Process Instance Numarası")
    private String processInstanceId;
    @JsonProperty("Task Türü")
    private String workItemName;
    @JsonProperty("Task Durumu")
    private String workItemState;
    @JsonProperty("Task Parametreleri")
    private Map<String, Object> workItemParameters;
}
