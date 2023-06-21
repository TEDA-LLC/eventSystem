package com.example.eventsystem.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SiteDTO {

    private String name, link, description;
    private Long departmentId;

}
