package com.example.springbootresttemplatejbpmrestapi.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData {

    private String type;
    private String msg;
    private Map<String, Map<String, List<Map<String, Object>>>> result;

}
