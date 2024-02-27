package com.example.springbootresttemplatejbpmrestapi.model;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericRequestBody {


    private  Map<String,BeyannameDTO> beyanname;
}
