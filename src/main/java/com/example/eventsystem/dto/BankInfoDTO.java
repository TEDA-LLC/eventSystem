package com.example.eventsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankInfoDTO {

    private String name, branch, currency;

    private Integer mfo, accountNumber;

}
