package com.example.springbootresttemplatejbpmrestapi.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorHandler {
    private String title;
    private int errorStatusNo;
    private String errorMessage;
}
