package com.example.springbootresttemplatejbpmrestapi.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericRequestInBody {
    private String packageName;
    private BeyannameDTO beyannameDTO;
}
