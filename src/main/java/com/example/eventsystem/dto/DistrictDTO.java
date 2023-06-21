package com.example.eventsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DistrictDTO {
    private String name;

    private Integer districtCode;

    private Long regionId;

}
