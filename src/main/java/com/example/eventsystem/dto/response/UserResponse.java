package com.example.eventsystem.dto.response;

import com.example.eventsystem.model.Call;
import com.example.eventsystem.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserResponse {

    private User user;

    private Call lastCall;

    private Integer callCount, requestCount;

}
