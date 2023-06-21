package com.example.eventsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Malikov Azizjon  *  16.01.2023  *  17:41   *  IbratClub
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    private String nameUz, nameRu, nameEn, descriptionUz, descriptionRu, descriptionEn, textUz, textRu, textEn, agreeTextUz, agreeTextEn, agreeTextRu;
    private List<Long> speakersId;
    private Long categoryId;
    private AddressDTO address;
    private MultipartFile attachment;
    private String from, to;
    private Double price;

}
