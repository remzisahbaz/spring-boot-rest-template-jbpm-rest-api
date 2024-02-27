package com.example.springbootresttemplatejbpmrestapi.model;

import jdk.jfr.Label;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JbpmProcessInstance {
    @Label("process-instance-state")
    private int processInstanceId;
}
