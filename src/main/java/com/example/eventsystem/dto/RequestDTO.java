package com.example.eventsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mansurov Abdusamad  *  26.11.2022  *  17:03   *  tedaUz
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestDTO {

    private String name, email, phone;

    private String aboutProduct, category, requestType;

    private Long userId, productId;
    private boolean agree;
}
