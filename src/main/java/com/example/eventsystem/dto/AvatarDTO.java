package com.example.eventsystem.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  09.12.2022  *  15:47   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AvatarDTO {

    private List<MultipartFile> photos;

    private Long userId;

    private String personal;

    private String  aboutWork;

    private String hobby;
}
