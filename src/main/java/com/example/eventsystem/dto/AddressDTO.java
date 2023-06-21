package com.example.eventsystem.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class AddressDTO {

    private Long districtId, regionId, countryId;
    private Double latitude, longitude;
    private String streetHome;
}
