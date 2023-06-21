package com.example.eventsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  13:50   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApiResponse<T> {

    private String message;
    @JsonIgnore
    private int status;
    private boolean success;
    private T data;

}
