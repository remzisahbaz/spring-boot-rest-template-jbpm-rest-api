package com.example.springbootresttemplatejbpmrestapi.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeyannameDTO {
    public long id;
    public String gtip;
    public String durum;
    public Boolean hata;
    public Double netmiktar;
}
